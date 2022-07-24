<template>
  <div class="app-container">
    <div slot="header" class="clearfix">
          <span>客户端管理</span>
    </div>
    <div class="filter-container">
      <el-input v-model="listQuery.qco.nameLike" placeholder="名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.qco.accountLike" placeholder="账号" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

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
      <el-table-column label="名称" prop="name" sortable align="center" width="140" >
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="账号" prop="account" sortable align="center" width="120" >
        <template slot-scope="{row}">
          <span>{{ row.account }}</span>
        </template>
      </el-table-column>
      <el-table-column label="授权方式" width="200px" align="center">
        <template slot-scope="{row}">
          <el-tag>{{ row.grantTypes }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="回调地址" align="center" >
        <template slot-scope="{row}">
          <span>{{ row.authCallbacks }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建人" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createUser }}</span>
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
          <el-button  size="mini" @click="handleRole(row)">
            分配角色
          </el-button>
          <el-button  size="mini" @click="handleViewAuth(row)">
            查看权限
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
        <el-form-item label="账号" prop="account">
          <el-input :disabled='temp.disabled' v-model="temp.account" placeholder="输入账号" />
        </el-form-item>
         <el-form-item label="密码" prop="passwd">
          <el-input :disabled='temp.disabled' v-model="temp.passwd" placeholder="输入密码" />
        </el-form-item>
        <el-form-item label="授权方式" prop="authTypes">
          <el-select :disabled='temp.disabled' multiple  v-model="temp.grantTypes" class="filter-item" placeholder="Please select">
            <el-option v-for="item in typeOptions" :key="item.value" :label="item.name" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="回调地址" >
          <el-input  :disabled='temp.disabled' :rows="3" type="textarea" v-model="temp.authCallbacks" placeholder="回调地址"/>
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

<el-dialog title="客户端角色分配" :visible.sync="dialogRoleVisible">
      <el-transfer
        filterable
        filter-placeholder="请输入"
        :props="roleProps"
        :titles="['Source', 'Target']"
        v-model="selectdRoles"
        :data="roleDatas">
      </el-transfer>
   
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRoleVisible = false">
          取消
        </el-button>
        <el-button  type="primary" @click="handleSubmitRole">
          保存
        </el-button>
      </div>
</el-dialog>

    <el-dialog title="客户端权限" :visible.sync="dialogResVisible">
       <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="菜单权限" name="menu">
            <el-tree
              ref="menuTree"
              :data="menuData"
              default-expand-all
              node-key="id"
              :props="defaultProps">
            </el-tree>
        </el-tab-pane>
        <el-tab-pane label="接口权限" name="iface">
          <el-tree
              ref="ifaceTree"
              :data="ifaceData"
               default-expand-all
              node-key="id"
              :props="defaultProps">
            </el-tree>
        </el-tab-pane>
        <el-tab-pane label="数据权限" name="data">          
          <el-tree
              ref="dataTree"
              :data="dataData"
               default-expand-all
              node-key="id"
              :props="defaultProps">
            </el-tree>
          </el-tab-pane>
      </el-tabs>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogResVisible = false">
          取消
        </el-button>
      </div>
     </el-dialog>
  </div>
</template>

<script>
import DictHelper from '@/utils/dict'
import { findPage, getById, save, update,exportPage,importSave, deleteById,getRoles,getResTree,saveRoles,getAllRoles } from '@/api/basic/client'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const typeOptions = DictHelper.getDicts("basic","GRANT_TYPE");
const typeKeyValue = DictHelper.getDictsMap("basic","GRANT_TYPE");

export default {
  name: 'BasicClient',
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
      _activeRow: null,
      _activeRowRoles: null,
      activeName:"menu",
      menuData:[],
      roleResIds:[],
      ifaceData:[],
      dataData:[],
      defaultProps: {
          children: 'children',
          label: 'name'
      },
      roleProps: {
          key: 'id',
          label: 'name'
      },
      dialogResVisible:false,
      dialogRoleVisible:false,
      selectdRoles:[],
      roleDatas:[],
      typeOptions,
      uploadShow: false,
      uploadKey: 0,
      fileList: [],
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        pageSize: 10,
        pageNum: 1,
        qco: {
          nameLike: undefined,
          type: undefined,
          accountLike: undefined
        },
        sort: '+name'
      },
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      temp: {
        id: undefined,
        name: '',
        memo: undefined,
        org: {name:''},
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
        account: [{ required: true, message: 'account is required', trigger: 'change' }],
        code: [{ required: true, message: 'code is required', trigger: 'blur' }]
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
        org: {name:''}
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
      if( !this.temp.org )
      {
          this.temp.org = {};
      }
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
      if( !this.temp.org )
      {
          this.temp.org = {};
      }
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
    handleViewAuth(row) {
      this.dialogRoleResVisible = true;
      this.activeName = "menu";
      this._activeRow = row;
       this.handleTabClick();
    },    
    handleTabClick() {
       this.dialogRoleResVisible = true;
       
       if( this.activeName === "menu")
       {
             getResTree(this._activeRow.id,null).then(response => {
                  this.menuData = response.data;
              });
       }
       else if( this.activeName === "iface")
       {
         
       }
       else{

       }

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
    handleRole(row){
      this.dialogRoleVisible = true;
      this._activeRow = row;
      this.roleDatas = [];
      this.selectdRoles = [];
      this._activeRowRoles = [];
      var query = {};
      getAllRoles(query).then(responce => {
        this.roleDatas = responce.data;
        getRoles(row.id).then(responce => {         
          var roleMaps = {};     
          var selectRoleIds = [];
          for(var role of responce.data)
          {
            selectRoleIds.push(role.id);
            roleMaps[role.id] = role;
          }
          this._activeRowRoles = roleMaps;     
         this.selectdRoles = selectRoleIds;
        });
      });
    },
    handleViewAuth(row) {
      this.dialogResVisible = true;
      this.activeName = "menu";
      this._activeRow = row;
       this.handleTabClick();
    },  
    handleTabClick() {
       var aname = this.activeName;
       this[aname +"Data"] = [];
        getResTree(this._activeRow.id,aname).then(response => {
            this[aname +"Data"] = response.data;            
        })

    },
    handleSubmitRole(){
      var clientRoleSaveBo = {};
      var roleIds = [];
      var roleMaps = this._activeRowRoles;     
      var delRoleIds = [];
      debugger
       for(var roleId of this.selectdRoles)
        {
           roleIds.push(roleId);
           delete roleMaps[roleId];
        }
      for( var roleId in roleMaps )
      {
          delRoleIds.push(roleId);
      }

      clientRoleSaveBo["clientId"] = this._activeRow.id;
      clientRoleSaveBo["roleIds"] = roleIds;
      clientRoleSaveBo["delRoleIds"] = delRoleIds;
      debugger
      saveRoles(clientRoleSaveBo).then(res => {
          this.dialogRoleVisible = false;
          this.roleDatas = [];
          this.selectdRoles = [];
          this._activeRowRoles = [];
           this.$notify({
              title: '成功',
              message: '保存客户端角色成功!',
              type: 'success',
              duration: 2000
            })

      });
    }
  }
}
</script>
