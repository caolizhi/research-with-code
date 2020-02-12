# tomcat cluster

1. [introduction](#introduction)
2. [open tomcat cluster switch](#open-tomcat-cluster-switch)
3. [what's tomcat cluster work flow](#whats-tomcat-cluster-work-flow)
4. [distributed system key part —— session manangement](#distributed-system-key-part--session-manangement)
5. [demo](#demo)
   - 5.1 [windows (single)](#windows-single)
   - 5.2 [linux (cluster + nginx)](#linux-cluster--nginx)

---

#### introduction
&emsp;&emsp;The tomcat cluster implementation provides session replication, context attribute replication and cluster wide WAR file deployment. <br>
&emsp;&emsp;Cluster configuration is fairly complex, the default configuration will work for most people out of the box.

#### open tomcat cluster switch
   1. simple template <br>
   `<Cluster className="org.apache.catalina.ha.tcp.SimpleTcpCluster"/>`
   <details>
   <summary><b>Expand</b>: detail default configuration:</summary>
   ```
   <Cluster className="org.apache.catalina.ha.tcp.SimpleTcpCluster"
                 channelSendOptions="8">

          <Manager className="org.apache.catalina.ha.session.DeltaManager"
                   expireSessionsOnShutdown="false"
                   notifyListenersOnReplication="true"/>

          <Channel className="org.apache.catalina.tribes.group.GroupChannel">
            <Membership className="org.apache.catalina.tribes.membership.McastService"
                        address="228.0.0.4"
                        port="45564"
                        frequency="500"
                        dropTime="3000"/>
            <Receiver className="org.apache.catalina.tribes.transport.nio.NioReceiver"
                      address="auto"
                      port="4000"
                      autoBind="100"
                      selectorTimeout="5000"
                      maxThreads="6"/>

            <Sender className="org.apache.catalina.tribes.transport.ReplicationTransmitter">
              <Transport className="org.apache.catalina.tribes.transport.nio.PooledParallelSender"/>
            </Sender>
            <Interceptor className="org.apache.catalina.tribes.group.interceptors.TcpFailureDetector"/>
            <Interceptor className="org.apache.catalina.tribes.group.interceptors.MessageDispatchInterceptor"/>
          </Channel>

          <Valve className="org.apache.catalina.ha.tcp.ReplicationValve"
                 filter=""/>
          <Valve className="org.apache.catalina.ha.session.JvmRouteBinderValve"/>

          <Deployer className="org.apache.catalina.ha.deploy.FarmWarDeployer"
                    tempDir="/tmp/war-temp/"
                    deployDir="/tmp/war-deploy/"
                    watchDir="/tmp/war-listen/"
                    watchEnabled="false"/>

          <ClusterListener className="org.apache.catalina.ha.session.ClusterSessionListener"/>
        </Cluster>
   ```
   </details>

   2. place
      - `<Engine>`
      
      - `<Host>`

#### what's tomcat cluster work flow


#### distributed system key part —— session manangement



#### demo



   - ##### windows (single)




   - ##### linux (cluster + nginx)
