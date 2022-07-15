<template>
  <div class="app-container">
    <div>用户</div>
    <div class="filter-container">

      <el-input v-model="listQuery.qco.nameLike" placeholder="名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-select v-model="listQuery.qco.sex" placeholder="性别" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in sexOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Add
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
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="名称" prop="name" sortable="custom" align="center" width="150" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="150px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.title }}</span>
          <el-tag>{{ row.sex | sexFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建人" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createUser }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否在线" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <el-tag :type="row.isOnline | isOnlineFilter">
            {{ row.isOnline | isOnlineFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" width="230" class-name="small-padding fixed-width">
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
          <el-input :disabled='temp.disabled' v-model="temp.name" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select :disabled='temp.disabled' v-model="temp.sex" class="filter-item" placeholder="Please select">
            <el-option v-for="item in sexOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <el-date-picker :disabled='temp.disabled' v-model="temp.timestamp" type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item label="创建人" prop="createUser">
          <el-input :disabled='temp.disabled' v-model="temp.createUser" />
        </el-form-item>

        <el-form-item label="是否在线">
          <el-select :disabled='temp.disabled' v-model="temp.isOnline" class="filter-item" placeholder="Please select">
            <el-option v-for="item in isOnlineOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
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

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
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
</template>

<script>
import { findPage, getById, save, update,exportPage,importSave, deleteById } from '@/api/myuser'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

const sexOptions = [
  { key: '0', display_name: '男' },
  { key: '1', display_name: '女' }
]

const isOnlineOptions = [
  { key: '0', display_name: '在线' },
  { key: '1', display_name: '离线' }
]


// arr to obj, such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'ComplexTable',
  components: { Pagination  },
  directives: { waves },
  filters: {
    isOnlineFilter(isOnline) {
      const statusMap = {
        "0": 'success',
        "1": 'danger'
      }
      return statusMap[isOnline] ||  "info";
    },
    sexFilter(key) {
      const map = {
        "0": '男',
        "1": '女'
      }
      return map[key];
    }
  },
  data() {
    return {
      uploadShow: false,
      uploadKey: 0,
      fileList: [],
      uploadUrl: "http://localhost:8080/curdie/curdieUser/import/save",
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        pageSize: 10,
        pageNum: 1,
        qco: {
          nameLike: undefined,
          sex: undefined
        },
        sort: '+name'
      },
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      sexOptions,
      isOnlineOptions,
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '更新',
        create: '新增',
        view: '查看'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        name: [{ required: true, message: 'type is required', trigger: 'change' }],
        sex: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询获取数据方法
    getList() {
      this.listLoading = true
      findPage(this.listQuery).then(response => {
        this.list = response.data.list;
        this.total = response.data.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
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
        sex: "1",
        isOnline: "0",
        disabled: false
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          this.temp.author = 'vue-element-admin'
          save(this.temp).then(() => {
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
    }
  }
}
</script>
