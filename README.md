# liqihua-admin是liqihua-basic的一个分支  
### 1.通过扩展mybatis plus的generator，生成controller、vo、vue的常规列表页和表单页  
### 2.shiro权限控制  
### 3.swagger文档  
### 4.使用Srping AOP打印请求日志  
### 5.使用Spring 的@ControllerAdvice注解做全局异常处理  

#  如何使用  
### 一、数据库配置
本地创建一个空的数据库  
找到项目的application.yml文件，修改里面的数据库连接为自己的连接  
启动项目，可以看到，基础表已经初始化好，如下  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image001.png)  
 其中test_person是演示用表，可以删除  
下面，来生成这个表的代码  
找到CodeGenerator这个代码工具类  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image002.png)  
 同样的，填好数据库连接配置，填好要生成哪个表  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image003.png)  
 右击——Run make()，打开目录，可以看到代码已经生成  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image004.png)  
 把java代码拷贝到项目里  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image005.png)  
 拷贝person到modules里  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image006.png)  
 拷贝后  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image007.png)  
 重启项目，打开http://localhost:9001/liqihua/api_doc/api.html，可以看到，增删查改已经生成好了  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image008.png)  
 前端部分，基于vue-cli。地址：https://github.com/liqihua/liqihua-admin-vue.git  
自己配好node环境、把前端代码跑起来  
回到刚刚生成代码的那个目录  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image009.png)  
 把testPerson文件夹copy到前端项目里  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image010.png)  
 copy后  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image011.png)  
 找到src/router/index.js，来写这两个页面的路由  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image012.png)  
 在一级路由“数据管理”下面填写增删查改的路由：  
 {  
  path: 'testPerson/list',  
  name: 'testPerson/list',  
  component: () => import('@/views/testPerson/list'),  
  meta: { title: '测试人员', icon: 'menu' }  
},  
{  
  path: 'testPerson/add',  
  name: 'testPerson/add',  
  component: () => import('@/views/testPerson/form'),  
  hidden: true  
},  
{  
  path: 'testPerson/edit/:id',  
  name: 'testPerson/edit',  
  component: () => import('@/views/testPerson/form'),  
  hidden: true  
}  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image013.png)  
 可以copy上面的代码，写好以后的路由是这样的  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image014.png)  
 打开前端地址，默认是http://localhost:9528/，默认超级账号是admin，密码是admin  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image015.png)  
 可以看到，刚刚新添加的路由，在左侧菜单中并没有显示出来  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image016.png)  
 这是因为没有配菜单和权限。  
添加菜单  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image017.png)  
 需要注意的是，路由名称要和刚刚添加的路由里面那个name一致  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image018.png)  
 给这个菜单添加增删查改的权限  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image019.png)  
 需要注意的是，权限符号要和刚刚生成的controller函数头部的权限符号一致，controller的每个函数都自动生成了一个权限符号  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image020.png)  
 继续添加保存的权限  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image021.png)  
 继续添加删除权限  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image022.png)  
 权限添加好了，现在给admin这个角色赋予刚刚添加的这些权限  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image023.png)  
 分配好权限，退出登录，重新登录一下，可以看到，菜单出来了  
