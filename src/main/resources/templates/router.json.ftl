{
  path: '${table.entityName?replace("Entity","")?uncap_first}/list',
  name: '${table.entityName?replace("Entity","")?uncap_first}/list',
  component: () => import('@/views/${table.entityName?replace("Entity","")?uncap_first}/list'),
  meta: { title: '教师管理', icon: 'menu' }
},
{
  path: '${table.entityName?replace("Entity","")?uncap_first}/add',
  name: '${table.entityName?replace("Entity","")?uncap_first}/add',
  component: () => import('@/views/${table.entityName?replace("Entity","")?uncap_first}/form'),
  hidden: true
},
{
  path: '${table.entityName?replace("Entity","")?uncap_first}/edit/:id',
  name: '${table.entityName?replace("Entity","")?uncap_first}/edit',
  component: () => import('@/views/${table.entityName?replace("Entity","")?uncap_first}/form'),
  hidden: true
}