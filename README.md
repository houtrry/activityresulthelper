# activityresulthelper  
通常我们会通过Activity#startActivityForResult方法处理从下个页面返回参数回到上个页面的情况, 然后重写上个页面的onActivityResult方法, 接收第二个页面返回值.Activity#startActivityForResult和Activity#onActivityResult方法分离, 不便于使用, 逻辑也不清晰.  
该库是Activity#startActivityForResult方法的辅助. 通过回调接收下个页面的返回值.   
## 使用 
```
val builder = ActivityResultRequest.Builder()
        builder
            .apply {
                context = this@MainActivity
                targetActivityCls = Main2Activity::class.java
                fragmentManager = this@MainActivity.supportFragmentManager
                onResultCallback = object :OnActivityResultCallback{
                    override fun onActivityResult(resultCode: Int, resultIntent: Intent?, isOk: Boolean) {
                        Log.d("", "resultCode: $resultCode, isOk: $isOk, resultIntent: $resultIntent")
                    }
                }
            }
            .withBoolean(KEY_NAME, true, false)
            .withInt("abc", 12)
            .withString("abc", "efg")
            .build()
            .start()
```
或者
```
        val builder = ActivityResultRequest.Builder()
        builder.fromActivity(this)
            .toActivity(Main2Activity::class.java)
            .onActivityResultCallback(object :OnActivityResultCallback{
                override fun onActivityResult(resultCode: Int, resultIntent: Intent?, isOk: Boolean) {
                    Log.d("", "resultCode: $resultCode, isOk: $isOk, resultIntent: $resultIntent")
                }
            })
            .withString("abc", "efg")
            .build()
            .start()
```

## 原理
实际上也是通过Activity#startActivityForResult实现的页面跳转, 只不过在跳转页面前, 在当前页面添加一个没有布局的Fragment, 重写Fragment#onActivityResult, 在Fragment#onActivityResult里处理下个页面的返回值.具体逻辑可以参考代码.
