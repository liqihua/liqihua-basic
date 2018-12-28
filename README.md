# liqihua-admin
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
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image014.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image015.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image016.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image017.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image018.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image019.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image020.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image021.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image022.png)  
 ![image](https://github.com/liqihua/readme_images/blob/master/liqihua-basic/image023.png)  
