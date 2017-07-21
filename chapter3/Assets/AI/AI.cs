using System.Collections;
using UnityEngine;
using System.Collections.Generic;
public class AI : MonoBehaviour {

	//所控制的坦克
	public Tank tank;
	//初始化固定路线
	private Path path = new Path();
	/*
	*主动搜寻算法的变量
	*/
	//锁定的目标
	private GameObject target;
	//搜寻范围
	private float sightDistance = 30;
	//上一次搜寻时间
	private float lastSearchTargetTime = 0;
	//搜寻间隔
	private float searchTargetInterval = 3;

	//枚举状态
	public enum Status{
		Patrol,	   //移动
		Attack,    //攻击状态
		Escape	   //逃跑
	}
	private Status status = Status.Patrol;
	//更改状态
	public void ChangeStatus(Status status){
		if(status == Status.Patrol)
			patrolStart();
		else if(status == Status.Attack)
			AttackStart();
	}
	//初始化路点
	void InitWaypoint(){
		GameObject obj = GameObject.Find("WaypointContainer");
		if(obj)
			path.InitByObj(obj,false);
	}
	void Start(){
		InitWaypoint();
	}

	// Update is called once per frame
	void Update () {
		if(tank.ctrlType != Tank.CtrlType.computer)
			return;
		if(status == Status.Patrol)
			PatrolUpdate();
		else if(status == Status.Attack)
			AttackUpdate();
		TargetUpdate();
		//move
		if(path.IsReach(transform)){
			path.NextWayPoint ();
		}
	}
	//移动的初始化
	void patrolStart(){
		
	}
	//攻击初始化
	void AttackStart(){
		
	}
	//移动中
	void PatrolUpdate(){
		
	}
	//攻击中
	void AttackUpdate(){
		
	}

	//主动搜寻算法
	void TargetUpdate(){
		float interval = Time.time - lastSearchTargetTime;
		if(interval < searchTargetInterval)
			return;
		lastSearchTargetTime = Time.time;
		//已有目标
		if(null != target){
			HasTarget();
		}else{
			NoTarget();
		}
	}

	void HasTarget(){
		Tank targetTank = target.GetComponent<Tank>();
		Vector3 pos = transform.position;
		Vector3 targetPos = target.transform.position;
		if(targetTank.ctrlType == Tank.CtrlType.none){
			Debug.Log("目标死亡，丢失目标");
			target = null;
		}else if(Vector3.Distance(pos,targetPos) > sightDistance){
			Debug.Log("距离过远，丢失目标");
			target = null;
		}
		 
	}

	void NoTarget(){
		//最小生命值
		float minHp = float.MaxValue;
		GameObject[] targets = GameObject.FindGameObjectsWithTag("Tank");
		for(int i = 0 ; i < targets.Length ; i ++){
			//Tank组件
			Tank tank = targets[i].GetComponent<Tank>();
			if(null == tank || targets[i] == gameObject || tank.ctrlType == Tank.CtrlType.none){
				continue;
			}
			//判断距离
			Vector3 pos = transform.position;
			Vector3 targetPos = targets[i].transform.position;
			if(Vector3.Distance(pos,targetPos) > sightDistance)
				continue;
			//判断生命值
			if(minHp > tank.hp)
				target = tank.gameObject;
		}
		if(null != target)
			Debug.Log("获取目标" + target.name);
	}

	public void OnAttecked(GameObject attackTank){
		target = attackTank;
	}

	//AI射击
	public bool IsShoot(){
		if (null == target) {
			return false;
		}
		//目标角度差
		float turretRoll = tank.turret.eulerAngles.y;
		float angle = turretRoll - GetTurretTarget ().y;
		if (angle < 0)
			angle += 360;
		if (angle < 30 || angle > 330)
			return true;
		else
			return false;
	}

	//移动炮塔状态机
	public Vector3 GetTurretTarget(){
		//没有目标
		if (null == target) {
			float y = transform.eulerAngles.y;
			Vector3 rot = new Vector3 (0, y, 0);
			return rot;
		} else {
			Vector3 pos = transform.position;
			Vector3 targetPos = target.transform.position;
			Vector3 vec = targetPos - pos;
			return Quaternion.LookRotation (vec).eulerAngles;
		}
	}
	//移动状态机的获取转向角
	public float GetSteering(){
		if(null == tank)
			return 0;
		//该路点和当前坦克的相对位置
		Vector3 itp = transform.InverseTransformPoint(path.waypoint);
		if(itp.x > path.deviation / 5)
			return tank.maxSteeringAngle;
		else if(itp.x < -path.deviation / 5)
			return -tank.maxSteeringAngle;
		else
			return 0;
	}
	//移动状态机的获取马力
	public float GetMotor(){
		if(null == tank)
			return 0;
		Vector3 itp = transform.InverseTransformPoint(path.waypoint);
		float x = itp.x;
		float z = itp.z;
		float r = 6;
		//定义了一个后退的区域和一个前进的区域
		if(z < 0 && Mathf.Abs(x) < -z && Mathf.Abs(x) < r)
			return -tank.maxMotorTorque;
		else
			return tank.maxMotorTorque;
	}
	//移动状态机的获取刹车
	public float GetBrakeTorque(){
		if(path.isFinish)
			return tank.maxMotorTorque;
		else
			return 0;
	}
}
