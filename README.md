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

## 思考
系统为什么不把onActivityResult设计成回调? 使用回调的话, 会有什么问题?
    提示:AActivity->BActivity后, 如果AActivity因为系统内存不足被系统回收后, BActivity中调用setResult方法会发生什么? 系统会重建AActivity, 但是这个重建的AActivity与回调这个匿名内部类所持有的AActivity是同一个吗? 如果不是同一个, 会有什么问题? 如何解决这个问题?
