package com.monkeyshen.appframe.appframelibrary.network_engine;

/**
 * Description:网络引擎类
 * Created by MonkeyShen on 2017/2/20.
 * Mail:shenminjie92@sina.com
 */

public class NetworkEngineManager {

    /**
     * 网络引擎管理器
     */
    private static NetworkEngineManager manager;
    private NetworkEngine networkEngine;

    private NetworkEngineManager() {
        networkEngine = new NetworkEngineImpl();
    }

    /**
     * 获取网络引擎管理器
     *
     * @return return
     */
    public static NetworkEngineManager getInstance() {
        if (manager == null) {
            synchronized (NetworkEngineManager.class) {
                if (manager == null) {
                    manager = new NetworkEngineManager();
                }
            }
        }
        return manager;
    }

    /**
     * 获取网络引擎
     *
     * @return return
     */
    public NetworkEngine getNetworkEngine() {
        return networkEngine;
    }


}
