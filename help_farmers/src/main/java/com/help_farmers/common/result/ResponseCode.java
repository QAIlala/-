package com.help_farmers.common.result;

/**
 * 作者：wz
 * 时间：2023/5/24 15:46
 * 描述： 把系统中所有异常 和 正常信息 都枚举出来
 */
public enum ResponseCode {

    SUCCESS("200","成功"),

    SYSTEM_ERROR("500","发生未知错误，请联系管理员"),

    NO_AUTHORITY("301", "暂无访问权限，请联系管理员"),

    NO_Login("304","当前用户还没有登录，请先登录"),

    TOKEN_INVALIDATE("305", "token非法"),

    USERNAME_OR_PASSWORD_INVALID("310", "用户名或密码错误"),

    USERNAME_OR_PASSWORD_NOTBLANK("311", "用户名或密码不能为空"),

    USER_NOT_FOUND("312","用户名不存在或用户已注销"),

    OLDPASSWORD_NOT_BLANK("313", "原始密码不能为空"),

    NEWPASSWORD_NOT_BLANK("314", "新密码不能为空"),

    OLDPASSWORD_INPUT_ERROR("315", "原始密码输入错误"),

    NEWPASSWORD_EQUAL_TO_OLDPASSWORD("316", "新旧密码不能一致"),

    PASSWORD_UPDATE_FAILD("317", "用户密码更新失败"),

    PLEASE_SELECT_DEL_CONTENT("318", "请选择待删除的内容"),

    USER_DEL_ERROR("319", "用户记录删除失败"),

    // 删除用户角色表
    USER_DEL_ERROR_ASSIGNED("320", "用户记录删除失败(yj)"),

    USERNAME_NOTBLANK("321", "用户名不能为空"),

    USER_UPDATE_FAILD("322", "用户更新失败"),

    USER_ROLE_ASSIGNED_FAILED("323", "用户角色分配失败"),

    USER_SAVE_ERROR("324", "用户添加失败"),

    ROLE_ISNOTEXIST("325", "当前角色不存在"),

    GRANT_DEL_FAILED("326", "角色授权失败"),

    // 删除用户角色表
    ROLE_DEL_FAILED_ASSIGNED("327", "角色删除失败(yj)"),

    // 角色权限表
    GRANT_DEL_FAILED_ASSIGNED("328", "角色授权失败(rg)"),

    // 角色动作表
    ACTIONS_DEL_FAILED("329", "动作权限授权失败(dq)"),

    ACTIONS_DEL_FAILED_ASSIGNED("330", "动作权限授权失败"),

    ROLENAME_ISNOTBLANK("331", "角色名不能为空"),

    ROLENAME_ISEXIST("332", "角色名已存在"),

    ROLE_SAVA_ERROR("333", "角色添加失败"),

    ROLE_UPD_ERROR("334", "角色更新失败"),

    DEL_CONTENT_ISNOTEXIST("335", "待删除内容不存在"),

    ROLE_DEL_FAILED("336", "角色删除失败"),

    NUMBER_IS_NOT_NULL("337", "编号不能为空"),

    NAME_IS_NOT_NULL("338", "名称不能为空"),

    UPDATE_FAILED("339", "更新失败"),

    SAVE_FAILED("340", "添加失败"),

    NUMBER_IS_EXSIT("341", "编号已存在"),

    DEL_FAILED("342", "删除失败"),

    NAME_IS_EXSIT("343", "名称已存在"),

    PARM_IS_BLANK("344", "参数必须全部填选（备注除外）"),

    STORAGE_FAILED("345", "入库失败"),

    OUTPUT_DEL_FAILED("346", "出库记录删除失败"),

    OUTPUT_FAILED("347", "出库失败"),

    INSUFFICIENT_INVENTORY("348", "库存不足"),

    ORDER_FAILED("349", "下单失败"),

    OPERATE_FAILED("350", "操作失败"),

    TOKEN_EXPIRE("351", "token已过期"),

    AUTH_FAILED("352", "认证失败请重新登录"),

    ROLE_CAN_NOT_DEL("353", "当前角色不可删除！"),

    WAREHOUSE_HAS_INVENTORY_NOW("354", "当前仓库还有库存，请先将农产品全部出库！"),

    WAREHOUSE_IS_NOT_BE_USED("355", "仓库还未投入使用"),

    ALREADY_ONLINE("356", "当前用户已登录")

    ;

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
