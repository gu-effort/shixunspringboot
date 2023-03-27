<template>
	<el-dialog title="编辑 Model" :visible.sync="dialogData.showEditDialog" width="450px" @close="cancelForm">
		<!--
			表单校验
				el-form 标签加上 ref、:model、:rules
				el-form-item 标签设置对应的 prop 属性
				props || data 设置对应的模型
		-->
		<el-form
			ref="model"
			:model="model"
			:rules="rules"
			:label-position="labelPosition"
			:label-width="formLabelWidth"
		>
			<el-form-item label="电影名" prop="name">
				<el-input v-model="model.name"></el-input>
			</el-form-item>
			<el-form-item label="发行日期">
				<el-date-picker
					v-model="model.releaseTime"
					type="date"
					placeholder="选择日期"
					value-format="yyyy-MM-dd"
				></el-date-picker>
			</el-form-item>
			<el-form-item label="评分">
				<el-rate v-model="model.score"></el-rate>
			</el-form-item>
			<el-form-item label="导演">
				<el-input v-model="model.director"></el-input>
			</el-form-item>
			<el-form-item label="编剧">
				<el-input v-model="model.scriptWriter"></el-input>
			</el-form-item>
			<el-form-item label="地区">
				<el-input v-model="model.region"></el-input>
			</el-form-item>
			<el-form-item label="公司">
				<el-input v-model="model.company"></el-input>
			</el-form-item>
			<el-form-item label="主演">
				<el-input v-model="model.star"></el-input>
			</el-form-item>
			<el-form-item label="剧情">
				<el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="model.plot"> </el-input>
			</el-form-item>

			<el-form-item label="封面">
				<el-upload
					:action="fileUploadInfo.action"
					:headers="fileUploadInfo.headers"
					list-type="picture-card"
					:on-preview="handlePictureCardPreview"
					:on-remove="handleRemove"
					:on-success="handleSuccess"
					:before-upload="beforeUpload"
					:file-list="model.images"
				>
					<i class="el-icon-plus"></i>
				</el-upload>
				<el-dialog :visible.sync="dialogVisible">
					<img width="100%" :src="dialogImageUrl" alt="" />
				</el-dialog>
			</el-form-item>
		</el-form>

		<div slot="footer" class="dialog-footer">
			<el-button @click="cancelForm()">取 消</el-button>
			<el-button type="primary" @click="submitForm()">确 定</el-button>
		</div>
	</el-dialog>
</template>

<script>
export default {
	name: "FfilmEdit",
	props: {
		dialogData: {
			type: Object,
			default: {
				id: 0,
				showEditDialog: false,
			},
		},
	},
	data: function () {
		return {
			// 弹出框设置项
			formLabelWidth: "80px",
			labelPosition: "right",
			model: {
				id: 0,
				name: "",
				releaseTime: "",
				score: 0,
				director: "",
				scriptWriter: "",
				region: "",
				company: "",
				star: "",
				plot: "",
				cover: "",
				images: [],
			},
			rules: {
				name: [
					{
						required: true,
						message: "用户名不能为空",
						trigger: ["blur", "change"],
					},
				],
			},
			// 用户相关
			// user: this.$Storage.getItemForLs(this.$TestData.TOKEN_OBJECT_KEY),
			// 上传图片设置
			dialogImageUrl: "",
			dialogVisible: false,
			fileUploadInfo: {
				action: this.$Request.domain + "/api/common/image/film",
				// headers: {
				// 	Token: this.$Storage.getItemForLs(this.$TestData.TOKEN_KEY),
				// },
			},
		};
	},
	methods: {
		// 上传图片相关方法
		handleRemove(file, fileList) {},
		handlePictureCardPreview(file) {
			this.dialogImageUrl = this.$Request.domain + file.url;
			this.dialogVisible = true;
		},
		beforeUpload: function (file) {
			const isJPG = file.type === "image/jpeg";
			const isLt4M = file.size / 1024 / 1024 < 4;
			// if (!isJPG) {
			// 	this.$message.error("上传头像图片只能是 JPG 格式!");
			// }
			if (!isLt4M) {
				this.$message.error("上传头像图片大小不能超过 4 MB!");
			}
			return isLt4M;
		},
		handleSuccess: function (response, file, fileList) {
			if (response.code == 200) {
				var image = {};
				image.url = this.$Request.domain + response.data;
				image.src = this.$Request.domain + response.data;
				this.model.cover = response.data;
				this.model.images.push(image);
			} else {
				this.$message.error("上传图片失败");
			}
		},
		// 取消
		cancelForm: function () {
			this.dialogData.showEditDialog = false;
		},
		// 提交表单
		submitForm: function () {
			// 验证表单并提交 Model
			this.$refs["model"].validate((isValid) => {
				if (!isValid) return;

				var type = this.model.id == 0 ? "post" : "put";
				this.$Request
					.fetch_("/api/resource/film", type, this.model)
					.then((result) => {
						if (result.code == this.$Request.OK_CODE) {
							this.$refs["model"].resetFields();
							this.$emit("emitDialogData", false);
						} else {
							this.$message.info(result.message);
						}
					})
					.catch((error) => {
						this.$message.error("提交出错。");
					});
			});
		},
		// 改变富文本框事件
		change(val) {},
	},
	watch: {
		// 监听 dialogData.id，初始化模型
		dialogData: function (newVal, oldVal) {
			if (newVal.id == 0) {
				if (typeof this.$refs["model"] != "undefined") {
					// 重置 model
					this.model = {
						id: 0,
						name: "",
						releaseTime: "",
						score: 0,
						director: "",
						scriptWriter: "",
						region: "",
						company: "",
						star: "",
						plot: "",
						cover: "",
						images: [],
					};
					this.$refs["model"].resetFields();
				}
			} else {
				var url = "/api/resource/film/" + this.dialogData.id;
				this.$Request
					.fetch(url)
					.then((result) => {
						this.model = result;
						this.model.images = [];
						if (this.model.cover) {
							var image = {};
							image.url = this.$Request.domain + this.model.cover;
							image.src = this.$Request.domain + this.model.cover;
							this.model.images.push(image);
						}
					})
					.catch((error) => {
						this.$message.info("没有数据。");
					});
			}
		},
	},
	mounted: function () {},
};
</script>

<style scoped>
/* .el-select,
.el-input {
	width: 240px;
	float: left;
	margin-left: 10px;
} */
</style>
