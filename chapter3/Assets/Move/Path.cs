using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Path {
	//所有路点
	public Vector3[] waypoints;
	//当前路点索引
	public int index = -1;
	//当前的路点
	public Vector3 waypoint;
	//是否循环
	bool isLoop = false;
	//到达误差
	public float deviation = 3f;
	//是否完成
	public bool isFinish = false;

	//根据场景标识物生成路点
	//obj是路点容器
	public void InitByObj(GameObject obj , bool isLoop){
		int length = obj.transform.childCount;
		//如果没有子物体
		if(length == 0){
			waypoints = null;
			index = -1;
			Debug.Log("没有标示物");
			return;
		}
		//遍历物体填充数组
		waypoints = new Vector3[length];
		for(int i = 0 ; i < length ; i ++){
			Transform trans = obj.transform.GetChild(i);
			waypoints[i] = trans.position;
			Debug.Log(waypoints[i]);
		}
		index = 0;
		waypoint = waypoints[index];
		this.isLoop = isLoop;
		isFinish = false;
	}

	//是否到达目的地
	public bool IsReach(Transform trans){
		Vector3 pos = trans.position;
		float distance = Vector3.Distance(waypoint,pos);
		return distance < deviation;
	}

	//下一个路点
	public void NextWayPoint(){
		if(index < 0)
			return;
		if(index < waypoints.Length - 1)
			index ++;
		else {
			if(isLoop)
				index = 0;
			else 
				isFinish = true;
		}
		waypoint = waypoints[index];
	}
}
