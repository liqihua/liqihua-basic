<template>
    <div v-loading="loading" class="app-container">
        <el-tabs value="add" @tab-click="tabClick">
            <el-tab-pane label="${table.comment}列表" name="list"/>
            <el-tab-pane label="编辑${table.comment}" name="add"/>
        </el-tabs>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
            <#list table.fields as field>
            <#if field.propertyName != 'id' && field.propertyName != 'createDate' && field.propertyName != 'updateDate'>
            <el-form-item label="${field.comment}" prop="${field.propertyName}">
                <el-col :span="6">
                    <#if field.type?contains("bit") >
                    <el-radio-group v-model="form.${field.propertyName}">
                        <el-radio :label="true">true</el-radio>
                        <el-radio :label="false">false</el-radio>
                    </el-radio-group>
                    <#elseif field.type == 'date'>
                    <el-date-picker v-model="form.${field.propertyName}" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"/>
                    <#elseif field.type == 'datetime'>
                    <el-date-picker v-model="form.${field.propertyName}" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择时间"/>
                    <#elseif field.type == 'time'>
                    <el-time-picker v-model="form.${field.propertyName}" :picker-options="{ selectableRange: '00:00:00 - 23:59:59' }" value-format="HH:mm:ss" placeholder="选择时间"/>
                    <#elseif field.type?contains("text")>
                    <el-input v-model="form.${field.propertyName}" type="textarea"/>
                    <#else>
                    <el-input v-model="form.${field.propertyName}" type="text" clearable/>
                    </#if>
                </el-col>
            </el-form-item>
            </#if>
            </#list>
            <el-form-item>
                <el-button :loading="loading" type="primary" @click="onSubmit">提交</el-button>
                <el-button @click="onCancel">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { makeParam } from '@/utils/strutil'
import request from '@/utils/request'

const listPath = '/pro/${table.entityName?replace("Entity","")?uncap_first}/list'

export default {
    data() {
        return {
            loading: false,
            form: {
                <#list table.fields as field>
                <#if field.propertyName != 'createDate' && field.propertyName != 'updateDate'>
                ${field.propertyName}: null,
                </#if>
                </#list>
            },
            rules: {
                <#list table.fields as field>
                <#if field.propertyName != 'id' && field.propertyName != 'createDate' && field.propertyName != 'updateDate'>
                ${field.propertyName}: [{ required: true, trigger: 'blur', message: '${field.comment}不能为空' }],
                </#if>
                </#list>
            }
        }
    },
    created() {
        if (this.$route.params && this.$route.params.id) {
            this.loading = true
            request({
                url: '/sys/${entity?uncap_first?replace('Entity','WebController')}/get',
                method: 'get',
                params: { id: this.$route.params.id }
            }).then(response => {
                this.loading = false
                this.form = response.data
            }).catch(error => {
                console.log(error)
                this.loading = false
            })
        }
    },
    methods: {
        tabClick(tab) {
            if(tab.name == 'list') {
                this.$router.push(listPath)
            }
        },
        onSubmit() {
            this.$refs.form.validate(valid => {
                if(valid) {
                    this.loading = true
                    let param = makeParam(this.form)
                    return request({
                        url: '/sys/${entity?uncap_first?replace('Entity','WebController')}/save',
                        method: 'post',
                        data: param
                    }).then(() => {
                        this.loading = false
                        this.$message({
                            message: '保存成功',
                            type: 'success'
                        })
                        this.$router.push(listPath)
                    }).catch(error => {
                        console.log(error)
                        this.loading = false
                    })
                }
            })
        },
        onCancel() {
            this.$router.push(listPath);
        }
    }
}
</script>

<style scoped>

</style>

