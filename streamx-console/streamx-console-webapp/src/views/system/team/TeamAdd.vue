<template>
  <a-drawer
    :mask-closable="false"
    width="650"
    placement="right"
    :closable="true"
    @close="onClose"
    :visible="visible"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <template slot="title">
      <a-icon type="team" />
      Add Team
    </template>
    <a-form
      :form="form">
      <a-form-item
        label="Team Name"
        v-bind="formItemLayout"
        :validate-status="validateStatus"
        :help="help">
        <a-input
          @blur="handleTeamNameBlur"
          v-decorator="
            ['teamName',
             {rules: [{ required: true }]}]" />
      </a-form-item>

      <a-form-item
        label="manager"
        v-bind="formItemLayout">
        <a-input v-decorator="['manager',{rules: [{ required: false }]}]" />
      </a-form-item>

      <a-form-item
        label="mobile"
        v-bind="formItemLayout">
        <a-input
          v-decorator="['mobile', {rules: [
            { pattern: '^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$', message: '请输入正确的手机号'}
          ]}]" />
      </a-form-item>

    </a-form>
    <div
      class="drawer-bootom-button">
      <a-button
        @click="onClose">
        Cancel
      </a-button>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading">
        Submit
      </a-button>
    </div>
  </a-drawer>
</template>
<script>
import { checkTeamName, post } from '@/api/team'

const formItemLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'TeamAdd',
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      loading: false,
      roleData: [],
      formItemLayout,
      form: this.$form.createForm(this),
      validateStatus: '',
      help: ''
    }
  },
  methods: {
    reset () {
      this.validateStatus = ''
      this.help = ''
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      if (this.validateStatus !== 'success') {
        this.handleTeamNameBlur()
      }
      this.form.validateFields((err, user) => {
        if (!err && this.validateStatus === 'success') {
          post({
            ...user
          }).then((r) => {
            if (r.status === 'success') {
              this.reset()
              this.$emit('success')
            }
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    handleTeamNameBlur (e) {
      const username = (e && e.target.value) || ''
      if (username.length) {
        if (username.length > 20) {
          this.validateStatus = 'error'
          this.help = '用户名不能超过10个字符'
        } else if (username.length < 4) {
          this.validateStatus = 'error'
          this.help = '用户名不能少于4个字符'
        } else {
          this.validateStatus = 'validating'
          checkTeamName({
            username: username
          }).then((r) => {
            if (r.data) {
              this.validateStatus = 'success'
              this.help = ''
            } else {
              this.validateStatus = 'error'
              this.help = '抱歉，该用户名已存在'
            }
          })
        }
      } else {
        this.validateStatus = 'error'
        this.help = '用户名不能为空'
      }
    }
  },
  watch: {
    visible () {
      if (this.visible) {

      }
    }
  }
}
</script>
