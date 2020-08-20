
## OneRadio 电台
> 简单地一个电台，为记录自己编程、认知水平，供以后回首。
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

![image-20200820120213736](https://cdn.jsdelivr.net/gh/gentlemanhu/public-store/images/image-20200820120213736.png)

### 初期的prototype规划

![image-20200820120036306](https://cdn.jsdelivr.net/gh/gentlemanhu/public-store/images/image-20200820120036306.png)

### 用到的NeteasemusicAPI接口

![image-20200820115927922](https://cdn.jsdelivr.net/gh/gentlemanhu/public-store/images/image-20200820115927922.png)

### 部分截图和思路
—> [PART](https://github.com/GentlemanHu/OneRadio/blob/stable/PART.md) <---

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

## 后记

- 本项目为初学项目，各种规范和风格尚未确立。
- 各个界面和风格为个人口味。
- 初衷为记录学习过程，供回首，总结和进步。
- 功能简单且不完善，通过后期学习使其完美。



> 我思故我在. I am not my body , and I am not even my mind.


# OneRadio

One Radio ,One Feel, One World.   One app full of radio and love.


