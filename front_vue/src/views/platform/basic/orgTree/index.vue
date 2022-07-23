<template>
  <el-container>
    <el-aside width="220px" >
      <el-input placeholder="输入关键字进行过滤" v-model="filterText" />
      <el-tree  ref="tree"  highlight-current :data="orgTypes" :props="orgTreeTypeProps" @node-click="handleNodeClick"/>
    </el-aside>
    <el-main>
  <div class="app-container">
    <div slot="header" class="clearfix">
          <span>机构树管理</span>
          <el-divider></el-divider>
    </div>
    <div class="filter-container">
      <el-input v-model="listQuery.qco.nameLike" placeholder="名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.qco.urlLike" placeholder="编码" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
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
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="名称" prop="name" sortable  >
        <template slot-scope="{row}">
          <span>{{ row.org.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="编码" prop="code" sortable align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.org.code }}</span>
        </template>
      </el-table-column>
      <el-table-column label="简称" prop="type" align="center" width="80" >
        <template slot-scope="{row}">
          <span>{{ row.org.shortName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="扩展字段1" prop="url" align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.org.ext1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="扩展字段2" prop="url" align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.org.ext2 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="扩展字段3" prop="url" align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.org.ext3 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Actions" align="center" width="320" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
           <el-button type="primary" size="mini" @click="handleCreate(row)">
            Add 
          </el-button>
          <el-button type="primary" size="mini" @click="handleEdit()">
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


 <el-dialog title="机构树编辑" :visible.sync="dialogEditTreeVisible">
            <el-tree
              ref="orgTree"
              :data="orgTreeData"
              show-checkbox
               default-expand-all
               draggable
              node-key="orgId"
              :props="defaultProps">
            </el-tree>

       <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditTreeVisible = false">
          取消
        </el-button>
        <el-button  type="primary" @click="handleSaveTree">
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
import { findOrgTreeType,findTree, save, exportPage,importSave } from '@/api/basic/orgTree'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination


export default {
  name: 'BasicOrgTree',
  components: { Pagination  },
  directives: { waves },
  filters: {
    typeFilter(key)
    {
        return typeKeyValue[key]
    }
  },
  data() {
    return {
      orgTypes:[],
      orgTreeData:[],
      dialogEditTreeVisible:false,
      rowOrgType:null,
      defaultProps: {
        key: 'orgId',
        children: 'children',
        label: 'name'
      },
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
          orgTypeId: undefined
        },
        sort: '+name'
      },
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      temp: {
        id: undefined,
        name: '',
        parentResId: undefined,
        memo: undefined,
        appId: undefined,
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
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        code: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      
      downloadLoading: false
    }
  },
  created() {
    this.getOrgTreeTypeList()
  },
  methods: {
    getOrgTreeTypeList(){
       findOrgTreeType({}).then(response => {
         this.orgTypes = response.data
       })
    },
    // 查询获取数据方法
    getList() {
      this.listLoading = true
      findTree(this.listQuery.qco).then(response => {
        if( response.data )
        {
          this.list = response.data;
          this.total = response.data.length;
        }
        else{
          this.list = [];
          this.total = 0;
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
    handleCreate(row) {
      this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
        })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
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
        this.rowOrgType = data;
        this.listQuery.qco.orgTypeId = data.id;
        this.getList();
      },
    handleSaveTree(){
        var orgTrees = [];
        var saveData = {"orgTypeId":this.rowOrgType.id,"orgTrees":orgTrees};
        orgTrees = this.$refs["orgTree"].getCheckedNodes();
        
        saveTree(saveData).then(response =>{
              this.$notify({
                title: 'Success',
                message: 'saveOrgTree Successfully',
                type: 'success',
                duration: 2000
              })
        })
    },
    handleEdit() {
      debugger
      this.dialogEditTreeVisible = true;
      this.listLoading = true;
      var qco = {"orgTreeTypeId":this.rowOrgType.id};
      var pqco = {"orgTreeTypeId":this.rowOrgType.sourceId};
      if( this.rowOrgType.sourceId )
      {
        findTree(pqco).then(pres => {
          this.orgTreeData = pres.data;
          findTree(qco).then(response => {
            var list = response.data;
              debugger
              for(var bo of list)
              {
                  this.$refs["orgTree"].setChecked(bo.orgId,true,false);
              }     

            this.listLoading = true;
          });
        });
      }
      else{
        findTree(qco).then(pres => {
          this.orgTreeData = pres.data;
        });
      }

    }
  }
}
</script>
