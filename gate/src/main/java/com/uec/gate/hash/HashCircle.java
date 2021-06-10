package com.uec.gate.hash;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.*;

@Component
public class HashCircle {
    HashMap<Integer,URI> map;
    Set<URI> uriSet;
    int maxEndPointNum;
    //键是虚拟节点的hash值， URI是实际节点的uri
    int currentEndPointNum;
    List<Integer> allowPorts;

    public HashCircle(){
        map = new HashMap<>();
        uriSet = new HashSet<>();
        currentEndPointNum = 0;
        maxEndPointNum = 60;
        for (int i = 0; i < maxEndPointNum; i++) {
            map.put(i,null);
        }
        allowPorts = new ArrayList<>();
        allowPorts.add(8089);
    }

    //假设虚拟节点只有60个，实际节点也不超过60个，不考虑虚拟节点扩容问题

    public void addEndPoint(URI uri){
        if(!uriSet.add(uri)) return;
        reMap();
    }

    public void delEndPoint(URI uri){
        if(!uriSet.remove(uri)) return;
        reMap();
    }

    private void reMap(){
        if(currentEndPointNum!=uriSet.size()){
            synchronized (this){
                List<URI> tmpList = new ArrayList<>(uriSet);
                int tmpListSize = tmpList.size();
                for(int i=0;i<maxEndPointNum;i++){
                    map.put(i,tmpList.get(i%tmpListSize));
                }
            }
            currentEndPointNum = uriSet.size();
        }
    }

    //根据当前秒数取得应该路由到的uri
    public URI getURI(){
        int secondNum = Calendar.getInstance().get(Calendar.SECOND);
        return map.get(secondNum);
    }

    //在定义域内
    public boolean domainInclude(URI uri){
        for(int port: allowPorts){
            if(uri.getPort()==port) return true;
        }
        return false;
    }

}
