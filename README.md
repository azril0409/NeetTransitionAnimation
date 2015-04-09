# NeetTransitionAnimation
NeetTransitionAnimation是一款Android用的轉場動畫，<br/>
支援Android 3.0以上，<br/>
透過Activity與Activity、Fragment與Activity相同類型與Id的View，<br/>
以及Android的lifecycle，<br/>
達成View縮放與移動的過場效果。<br/>
<br/>
目前可支援繼承TextView與ImageView的View
<br/>
##Compatibilty
API 11+
## Usage
###Activity:<br/>
```
NeetTransitionManager manager;

public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  ...
  manager = NeetTransitionSystem.createInstance(this);
  manager.onAfterCreateView(savedInstanceState);
}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  manager.onActivityResult(requestCode, resultCode, data);
}
```
###Fragment
```
NeetTransitionManager manager;

public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	...
	manager = NeetTransitionSystem.createInstance(this);
}

public void onActivityCreated(@Nullable Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	manager.onAfterCreateView(savedInstanceState);
}

public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	manager.onActivityResult(requestCode, resultCode, data);
}

public void onDestroyView() {
	manager.beforeFinish();
	super.onDestroyView();
}
```
###AndroidManifest.xml
```
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```
###When start Activity
```
manager.startActivity(new Intent(this,Activity2.class),ViewIds...);
```
###When add Fragment
```
manager.addFragment(R.id.fragment, new Fragment2(),ViewIds...);
```
