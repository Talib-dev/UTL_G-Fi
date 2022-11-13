package com.kura.utl.ui.deviceconfiguration

import android.util.Log
import java.io.*
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.UnknownHostException

class TCPClient {
    private var mSocket: Socket = Socket()

    private var mConnectListener: OnConnectionListener? = null

    private var mOut: PrintWriter? = null
    private var mIn: BufferedReader? = null

    @Volatile
    private var mRun = false
    private val mConnectionTimeout = 5000


    fun setListener(connectListener: OnConnectionListener) {
        mConnectListener = connectListener
    }


    fun connectInSeparateThread(ip: String, port: Int) {
        Thread { connect(ip, port) }.start()
    }

    private fun connect(ip: String, port: String) {
        connect(ip, Integer.valueOf(port))
    }

    private fun connect(ip: String, port: Int) {
        mRun = true
        var serverMessage: String?
        try {
            mSocket.connect(
                InetSocketAddress(InetAddress.getByName(ip), port),
                mConnectionTimeout
            )
            if (mConnectListener != null) {
                mConnectListener!!.connected(mSocket, ip, port)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (!mSocket.isClosed) {
                try {
                    mSocket.close()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
            try {
                Thread.sleep(2000)
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            if (mConnectListener != null) mConnectListener!!.disconnected(ip, port)
        }
        try {
            mIn = BufferedReader(InputStreamReader(mSocket.getInputStream()))
            mOut =
                PrintWriter(BufferedWriter(OutputStreamWriter(mSocket.getOutputStream())), true)
            while (mRun) {
                serverMessage = mIn!!.readLine()
                if (serverMessage != null && mConnectListener != null) mConnectListener!!.messageReceived(
                    serverMessage
                )
                if (serverMessage == null) mRun = false
            }
        } catch (e: Exception) {
            Log.e("TCPClient IO Exception", e.toString())
        } finally {
            try {
                mSocket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (mConnectListener != null) mConnectListener!!.disconnected(ip, port)
        }
    }


    @Throws(UnknownHostException::class)
    fun getIpFromDns(address: String?): String? {
        return InetAddress.getByName(address).hostAddress
    }

    fun send(message: String?) {
        if (mOut != null && !mOut!!.checkError()) {
            mOut!!.print(message)
            mOut!!.flush()
        }
    }

    fun sendLn(message: String?) {
        if (mOut != null && !mOut!!.checkError()) {
            mOut!!.println(message)
            mOut!!.flush()
        }
    }

    fun stopClient() {
        mRun = false
        try {
            if (!mSocket.isClosed) {
                mSocket.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun isConnected(): Boolean {
        return mSocket.isConnected
    }

    fun isClosed(): Boolean {
        return mSocket.isClosed
    }


    //----------------------------------------[Interfaces]----------------------------------------//

    interface OnConnectionListener {
        fun connected(socket: Socket, ip: String, port: Int)
        fun messageReceived(message: String)
        fun disconnected(ip: String, port: Int)


    }


}