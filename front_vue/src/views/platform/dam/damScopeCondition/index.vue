<template>
  <el-container>
    <el-aside width="220px" >
      <el-input placeholder="输入关键字进行过滤" v-model="filterText" />
      <el-tree  ref="tree"  highlight-current :data="damConfigs" :props="orgTreeTypeProps" @node-click="handleNodeClick"/>
    </el-aside>
    <el-main>
  <div class="app-container">
    <div slot="header" class="clearfix">
          <span>数据权限范围条件管理</span>
          <el-divider></el-divider>
    </div>
    <div class="filter-container">
      <el-input v-model="listQuery.qco.nameLike" placeholder="名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.qco.urlLike" placeholder="编码" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate()">
        Add
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleEdit()" >
        Edit
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        Export
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-upload2" @click="uploadShow=true">
        Import
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
       row-key="id"
       default-expand-all
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="名称" prop="name" sortable  >
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="编码" prop="code" sortable align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.code }}</span>
        </template>
      </el-table-column>

      <el-table-column label="属性名" prop="code" sortable align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.propertyName}}</span>
        </template>
      </el-table-column>      

      <el-table-column label="条件类型" prop="conditionType" sortable align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.conditionType }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作符" prop="operation" sortable align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.operation }}</span>
        </template>
      </el-table-column>

      <el-table-column label="值类型" prop="valueType" sortable align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.valueType }}</span>
        </template>
      </el-table-column>

      <el-table-column label="权限应用"  align="center" width="160" >
        <template slot-scope="{row}">
          <el-tag :key="tag" v-for="tag in row.applyTags">{{ tag }}</el-tag>

        </template>
      </el-table-column>

      <el-table-column label="Actions" align="center" width="320" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            Edit
          </el-button>
          <el-button  size="mini" @click="handleView(row)">
            View
          </el-button>
          <el-button  size="mini" type="danger" @click="handleDelete(row,$index)">
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getList" />


<el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="90px" style="width: 400px; margin-left:50px;">
        <el-form-item label="名称" prop="name">
          <el-input :disabled='temp.disabled' v-model="temp.name" placeholder="输入名称" />
        </el-form-item>
        <el-form-item label="编码" prop="code">
          <el-input :disabled='temp.disabled' v-model="temp.code" placeholder="请输入编码" />
        </el-form-item>
        <el-form-item label="属性名" prop="propertyName">
          <el-input :disabled='temp.disabled' v-model="temp.propertyName" placeholder="请输入属性名" />
        </el-form-item>
                <el-form-item label="条件类型" prop="conditionType">
          <el-select :disabled='temp.disabled' v-model="temp.conditionType" class="filter-item" placeholder="Please select">
            <el-option v-for="item in conditionTypeOptions" :key="item.value" :label="item.name" :value="item.value" />
          </el-select>
        </el-form-item>
                <el-form-item label="操作符" prop="operation">
          <el-input :disabled='temp.disabled' v-model="temp.operation" placeholder="请输入操作符" />
        </el-form-item>
                <el-form-item label="值类型" prop="valueType">
                    <el-select :disabled='temp.disabled' v-model="temp.valueType" class="filter-item" placeholder="Please select">
            <el-option v-for="item in valueTypeOptions" :key="item.value" :label="item.name" :value="item.value" />
          </el-select>
        </el-form-item>
                <el-form-item label="范围参数编码" prop="paramCode">
          <el-input :disabled='temp.disabled' v-model="temp.paramCode" placeholder="请输入范围参数编码" />
        </el-form-item>

        <el-form-item label="范围类型" prop="scopeType">
          <el-input :disabled='temp.disabled' v-model="temp.scopeType" placeholder="请输入范围类型" />
        </el-form-item>
                <el-form-item label="范围值" prop="scopeValue">
          <el-input :disabled='temp.disabled' v-model="temp.scopeValue" placeholder="请输入范围值" />
        </el-form-item>


      <el-form-item label="应用到查询" >
        <el-switch
            :disabled='temp.disabled'
            active-value="Y"
            inactive-value="N"
            v-model="temp.isApplyQuery">
          </el-switch>
      </el-form-item>
            <el-form-item label="应用到新增" >
        <el-switch
            :disabled='temp.disabled'
            active-value="Y"
            inactive-value="N"
            v-model="temp.isApplyAdd">
          </el-switch>
      </el-form-item>
            <el-form-item label="应用到修改" >
        <el-switch
            :disabled='temp.disabled'
            active-value="Y"
            inactive-value="N"
            v-model="temp.isApplyUpdate">
          </el-switch>
      </el-form-item>
            <el-form-item label="应用到删除" >
        <el-switch
            :disabled='temp.disabled'
            active-value="Y"
            inactive-value="N"
            v-model="temp.isApplyDelete">
          </el-switch>
      </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input :disabled='temp.disabled' :rows="3" type="textarea" v-model="temp.memo" placeholder="写点什么吧" />
        </el-form-item>

        <el-form-item label="创建时间" v-if="dialogStatus !== 'create'" prop="createTime">
          <el-input :disabled='temp.disabled' v-model="temp.createTime" />
        </el-form-item>
        <el-form-item label="创建人" v-if="dialogStatus !== 'create'" prop="createUser">
          <el-input :disabled='temp.disabled' v-model="temp.createUser" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button v-if="dialogStatus !== 'view'" type="primary" @click="dialogStatus==='create'?createData():updateData()">
          保存
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="uploadShow" title="导入">
      <el-upload
        ref="upload"
        :multiple="false"
        :file-list="fileList"
        :show-file-list="true"
        :auto-upload="false"
        :http-request="submitUpload"
        class="editor-slide-upload"
        action="#"
        :limit="1"
        list-type="picture-card"
      >
        <el-button size="small" type="primary">
          Click upload
        </el-button>
      </el-upload>
      <el-button @click="uploadShow = false">
        Cancel
      </el-button>
      <el-button type="primary" @click="handleSubmit">
        Confirm
      </el-button>
    </el-dialog>
  </div>
</el-main>
  </el-container>
</template>

<script>
import DictHelper from '@/utils/dict'
import { findConfig,findPage, save,update, exportPage,importSave } from '@/api/dam/damScopeCondition'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const valueTypeOptions = DictHelper.getDicts("dam","VALUE_TYPE");
const valueTypeKeyValue = DictHelper.getDictsMap("dam","VALUE_TYPE");

const conditionTypeOptions = DictHelper.getDicts("dam","CONDITION_TYPE");
const conditionTypeKeyValue = DictHelper.getDictsMap("dam","CONDITION_TYPE");

export default {
  name: 'DamScopeCondition',
  components: { Pagination  },
  directives: { waves },
  filters: {
    valueTypeFilter(key)
    {
        return valueTypeKeyValue[key]
    },
    conditionTypeFilter(key)
    {
        return conditionTypeKeyValue[key]
    }
  },
  data() {
    return {
      valueTypeOptions,
      conditionTypeOptions,
      damConfigs:[],
      orgTreeTypeProps: {
        label: 'name'
      },
      filterText: '',
      uploadShow: false,
      uploadKey: 0,
      fileList: [],
      tableKey: 0,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        pageSize: 100000,
        pageNum: 1,
        qco: {
          nameLike: undefined,
          urlLike: undefined,
          configId: undefined
        },
        sort: '+name'
      },
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      temp: {
        id: undefined,
        name: '',
        memo: undefined,
        configId: undefined,
        sortNum:100
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '更新',
        create: '新增',
        view: '查看'
      },
      rules: {
        name: [{ required: true, message: 'type is required', trigger: 'change' }],
        code: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      
      downloadLoading: false
    }
  },
  created() {
    this.getConfigList()
  },
  methods: {
    getConfigList(){
       findConfig({}).then(response => {
         this.damConfigs = response.data
       })
    },
    // 查询获取数据方法
    getList() {
      this.listLoading = true
      findPage(this.listQuery).then(response => {
        if( response.data )
        {
          this.list = response.data.list;
          this.total = response.data.total;
        }
        else{
          this.list = [];
          this.total = 0;
        }
        for(let d of this.list )
        {
          var applyTags = []
          d["applyTags"] = applyTags;
          if( d.isApplyQuery === 'Y')
          {
              applyTags.push("查询");
          } 
          if( d.isApplyAdd === 'Y')
          {
            applyTags.push("新增");
          }
          if( d.isApplyUpdate === 'Y')
          {
            applyTags.push("修改");
          }
          if( d.isApplyDelete === 'Y')
          {
            applyTags.push("删除");
          }
        }

        this.listLoading = false
      })
    },
    // 搜索方法，点击搜索
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        name: undefined,
        parentResId: undefined
      }
    },
    handleCreate() {
      this.resetTemp()
      this.temp.configId = this.listQuery.qco.configId;
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          save(this.temp).then((res) => {
            this.temp = res.data
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleView(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.temp.disabled=true;
      this.dialogStatus = 'view'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          update(tempData).then(() => {
            const index = this.list.findIndex(v => v.id === this.temp.id)
            this.list.splice(index, 1, this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Update Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 删除数据
    handleDelete(row, index) {
       deleteById(row.id).then(response => {
        this.$notify({
          title: 'Success',
          message: 'Delete Successfully',
          type: 'success',
          duration: 2000
        })
        this.list.splice(index, 1)
      })
    },
    handleDownload() {
      this.downloadLoading = true
      exportPage(this.listQuery).then(res => {
      this.downloadLoading = false
           var byteString = atob(res.data.content);
            var mimeString = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";//mime类型 -- image/png
          
            var ia = new Uint8Array(byteString.length);//创建视图
            for(var i = 0; i < byteString.length; i++) {
              ia[i] = byteString.charCodeAt(i);
            }
            var blob = new Blob([ia], {
              type: mimeString
            });
          // let blob = new Blob([res.data.content]);//type是文件类，详情可以参阅blob文件类型
          // 创建新的URL并指向File对象或者Blob对象的地址
          const blobURL = window.URL.createObjectURL(blob)
          // 创建a标签，用于跳转至下载链接
          const tempLink = document.createElement('a')
          tempLink.style.display = 'none'
          tempLink.href = blobURL
          tempLink.setAttribute('download', res.data.fileName)
          // 兼容：某些浏览器不支持HTML5的download属性
          if (typeof tempLink.download === 'undefined') {
            tempLink.setAttribute('target', '_blank')
          }
          // 挂载a标签
          document.body.appendChild(tempLink)
          tempLink.click()
          document.body.removeChild(tempLink)
          // 释放blob URL地址
          window.URL.revokeObjectURL(blobURL)
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    uploadSuccess(resData) {
      this.uploadShow = false
      this.uploadKey = this.uploadKey + 1
    },
    uploadClose() {
      this.uploadShow = false
    },
    handleSubmit(){
      this.$refs.upload.submit();
    },
    submitUpload (info) {
      const { file } = info
      this.file = file
      let formData = new FormData()
      formData.append('multipartFile', file)
      importSave(formData)
            .then(responce => {
                console.log(responce);
                let data = responce.data
                //上传成功的处理
              this.uploadShow = false 
              this.$notify({
                title: 'Success',
                message: 'Upload Successfully',
                type: 'success',
                duration: 2000
              })
            })
            .catch(error => {
                console.log(error.responce.data.error.message);
            });
    },
    handleNodeClick(data) {
        this.listQuery.qco.configId = data.id;
        this.getList();
      }
  }
}
</script>
