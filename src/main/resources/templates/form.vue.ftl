<template>
    <div v-loading="loading" class="app-container">
        <el-tabs value="add" @tab-click="tabClick">
            <el-tab-pane label="${table.comment}列表" name="list"/>
            <el-tab-pane label="新增${table.comment}" name="add"/>
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
                    <el-time-picker v-model="form.${field.propertyName}" :picker-options="{ selectableRange: '00:00:00 - 23:59:59' }" placeholder="选择时间"/>
                    <#elseif field.type?contains("text")>
                    <el-input v-model="form.${field.propertyName}" type="textarea"/>
                    <#else>
                    <el-input v-model="form.${field.propertyName}" type="text"/>
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

    },
    methods: {
        tabClick(tab) {
            if(tab.name == 'list') {
                this.$router.push('/${table.entityName?replace("Entity","")?uncap_first}/list')
            }
        },
        onSubmit() {

        },
        onCancel() {
            this.$router.push("/${table.entityName?replace("Entity","")?uncap_first}/list");
        }
    }
}
</script>

<style scoped>

</style>

