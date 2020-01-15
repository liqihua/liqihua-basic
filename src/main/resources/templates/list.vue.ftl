<template>
    <div v-loading="loading" class="app-container">
        <el-tabs value="list" @tab-click="tabClick">
            <el-tab-pane label="${table.comment}列表" name="list"/>
            <el-tab-pane label="新增${table.comment}" name="add"/>
        </el-tabs>
        <el-table :data="list" border fit highlight-current-row>
<#list table.fields as field>
    <#if field.propertyName != 'id'>
            <el-table-column label="${field.comment}" prop="${field.propertyName}" align="center"/>
    </#if>
</#list>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <router-link :to="'/pro/${table.entityName?replace("Entity","")?uncap_first}/edit/'+scope.row.id">编辑</router-link><br>
                    <a @click="doDelete(scope.row.id)">删除</a>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination background layout="total, sizes, prev, pager, next" :total="total" :page-sizes="[pageSize,60]" :page-size="pageSize" :current-page="page" @current-change="pageClick" @size-change="pageSizeChange"></el-pagination>
    </div>
</template>

<script>
import request from '@/utils/request'

const formPath = '/pro/${table.entityName?replace("Entity","")?uncap_first}/add'

export default {
    data() {
        return {
            loading: false,
            page: 1,
            pageSize: 30,
            total: 0,
            list: []
        }
    },
    created() {
        this.doPage()
    },
    methods: {
        tabClick(tab) {
            if(tab.name == 'add') {
                this.$router.push(formPath)
            }
        },
        doPage() {
            this.loading = true
            request({
                url: '/sys/${entity?uncap_first?replace('Entity','WebController')}/page',
                method: 'get',
                params: { page:this.page, pageSize:this.pageSize }
            }).then(response => {
                this.list = response.data.records
                this.total = response.data.total
                this.loading = false
            }).catch(error => {
                console.log(error)
                this.loading = false
            })
        },
        doDelete(id) {
            this.$confirm('确定删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.loading = true
                request({
                    url: '/sys/${entity?uncap_first?replace('Entity','WebController')}/delete',
                    method: 'post',
                    data: 'id=' + id
                }).then(response => {
                    this.doPage()
                    this.loading = false
                }).catch(error => {
                    console.log(error)
                    this.loading = false
                })
            })
        },
        pageClick(clickPage){
            if(clickPage != this.page){
                this.page = clickPage
                this.doPage()
            }
        },
        pageSizeChange(pageSize){
            this.pageSize = pageSize
            this.doPage()
        }
    }
}
</script>

<style scoped>

</style>
