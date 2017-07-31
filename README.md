# App快速开发框架

简述:一直以来想做个框架，直接引入，直接编写代码，不需要重新搭建，搭建项目很耗时间，所以做件很适合自己开发的一件事，仅适合于自身快速开发，框架模块以为library形式引入，
建议下载并且以为module形式引入

###基础框架

1.网络框架 okhttp<br/>
2.图片框架 fresco <br/>
3.mvp开发 <br/>
4.快速分页加载<br/>
5.通用组件，下拉刷新<br/>
6.多选/单选图片选择器，图片浏览器<br/>
7.基类activity、fragment、application<br/>
8.常用的数据解析，gson<br/>
9.权限申请<br/>
10.butterknife<br/>
11.普通的工具包<br/>
12.eventbus 事件处理<br/>
13.zbar 扫一扫


###陷进<br>
1.图片选择框架以及图片查看框架需要自行添加manifest<br/>
2.application gradle使用需要配置butterknife<br>
   `annotationProcessor 'com.jakewharton:butterknife-compiler:8.2.1`<br/>
3.继承BaseApplication,初始化工具类以及fresco



//不想写了，后面有机会再更新=。=#

<br/>



```

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```


```

compile 'com.github.shenminjie:AppFrameLibrary:v1.0.5'

```




