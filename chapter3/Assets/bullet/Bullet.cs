using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour {
	public float speed = 100f;
	public GameObject explode;
	public float maxLifeTime = 2f;
	//记录发射时间，就是发现这个子弹的时候
	public float instantiateTime = 0f;
	//攻击方
	public GameObject attackTank;
	// Use this for initialization
	void Start () {
		instantiateTime = Time.time; 
	}
	
	// Update is called once per frame
	void Update () {
		transform.position += transform.forward * speed * Time.deltaTime;
		if (Time.time - instantiateTime > maxLifeTime)
			Destroy (gameObject);
	}

	void OnCollisionEnter(Collision collisionInfo){
		if (collisionInfo.gameObject == attackTank) {
			return;
		}
		Instantiate (explode, transform.position, transform.rotation);
		Destroy (gameObject);
		//attact the target
		//判断是否有Tank组件
		Tank tank = collisionInfo.gameObject.GetComponent<Tank>();
		if (null != tank) {
			float att = GetAtt ();
			tank.BeAttacked (att,attackTank);
		}
	}

	public float GetAtt(){
		float att = 100 - (Time.time - instantiateTime) * 40;
		if(att < 1)
			att = 1;
		return att;
	}
}
