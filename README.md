<div align="center">
    <img src="https://img.shields.io/github/license/GentlemanHu/OneRadio">
    <img src="https://img.shields.io/github/stars/GentlemanHu/OneRadio">
    <img src="https://img.shields.io/github/forks/GentlemanHu/OneRadio">
</div>


## OneRadio 电台
> 简单的一个电台，为记录自己编程、认知水平，供以后回首。
---
#### 整体结构

<pre>
<code class="java">
    |- pers.hu.oneradio
            |- activity  //activities
            |- deal       //handler和一些异步处理类
            |- feel        //一些自定义组件、动画等
            |- net          //网络相关类
            |- utils        //工具类
    |- Home.java    // 主activity
</code>
</pre>
### 整体实现

![整体](https://cdn.jsdelivr.net/gh/gentlemanhu/public-store/images/20200820143812.png)

### 初期的prototype规划

![OneRadio](https://cdn.jsdelivr.net/gh/gentlemanhu/public-store/images/20200820143902.png)

### 用到的NeteasemusicAPI接口

![OneRadio-](https://cdn.jsdelivr.net/gh/gentlemanhu/public-store/images/20200820143924.png)

### 部分截图和思路

—> [PART](https://github.com/GentlemanHu/OneRadio/blob/stable/PART.md) <— 图

—> [视频演示](https://streamja.com/5raGz)<---视频

|                             Load                             |                             Main                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![afe1](https://cdn.jsdelivr.net/gh/gentlemanhu/public-store/images/20200822174744.gif) | ![afer2](https://cdn.jsdelivr.net/gh/gentlemanhu/public-store/images/20200822174821.gif) |



### 初期功能设想

- [ ] 计划实现
  - [x] 收听电台
  - [x] 切换电台
  - [x] 随机电台
  - [x] 手机下拉显示控制器
  - [x] 电台配图
  - [ ] 收听历史记录
  - [ ] 收听类型选择
  - [ ] 电台录音（缓存）和回放
  - [ ] 电台黑名单和白名单
  - [ ] 其他待灵感闪现..

## 问题
- [x] 获取歌曲链接异步处理，防止阻塞主线程
- [x] 动态渲染电台列表
- [x] 更舒服的响应，异步网络请求时候加上动画
- [ ] 代码重构，适配，应用优秀设计原则和模式，符合一般规范 `!important`
- [ ] 大多数只是为了实现而实现，并未采取最优资源和性能，需要重构 `!important`
- [ ] 初步编写时为了方便，context传入混乱，有内存泄露问题 `!important`
- [ ] 缓存处理，自动清理规则
- [ ] 电台实时收听和回放区分，处理实时音频流
- [ ] 简化代码，图片等缩减体积，提升启动性能
- [ ] 待发现..

## TODO

- [ ] boom 菜单优化，完善about界面
- [ ] music manager管理器
- [ ] 加载逻辑和动画完善
- [ ] 背景虚化度用户设置可调节实现
- [ ] 更优雅的启动，更好的响应，图片缓存等
- [ ] 更好的线程控制
- [ ] 更好的异步处理方式
- [ ] 更好的并行和并发处理控制
- [ ] 待灵感闪现

---
<details>
<summary>Dependencies</summary>
<pre>
<code class="bash">
implementation 'com.squareup.okhttp3:okhttp:4.7.2'
implementation fileTree(dir: 'libs', include: ['*.jar'])
implementation 'com.lzx:StarrySkyKt:2.4.2'
implementation 'com.github.florent37:materialviewpager:1.2.3'
implementation 'com.flaviofaria:kenburnsview:1.0.7'
implementation 'com.jpardogo.materialtabstrip:library:1.1.0'
implementation 'com.github.bumptech.glide:glide:4.0.0'
//RichPath
implementation 'com.github.tarek360.RichPath:animator:0.1.1'
//menu pop bom!
implementation 'com.nightonke:boommenu:2.1.1'
//slide image CardSlider   https://github.com/Ramotion/cardslider-android
implementation 'com.ramotion.cardslider:card-slider:0.3.1'
// image loader
implementation 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
// blur bg
implementation 'jp.wasabeef:blurry:3.0.0'
implementation 'com.gauravk.audiovisualizer:audiovisualizer:0.9.2'
// loading animation
implementation 'com.github.ybq:Android-SpinKit:1.4.0'
</code>
</pre>
</details>

## 后记

- 本项目为初学项目，各种规范和风格尚未确立。
- 各个界面和风格为个人口味。
- 初衷为记录学习过程，供回首，总结和进步。
- 功能简单且不完善，通过后期学习使其完美。



> 我思故我在. I am not my body , and I am not even my mind.

# OneRadio

One Radio ,One Feel, One World.   One app full of radio and love.


