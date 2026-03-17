-- 创建数据库
CREATE DATABASE IF NOT EXISTS homeworkai DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE homeworkai;

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(255) COMMENT '头像',
    status TINYINT DEFAULT 1 COMMENT '状态：1启用 0禁用',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    name VARCHAR(50) NOT NULL COMMENT '角色名称',
    code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    description VARCHAR(255) COMMENT '角色描述',
    status TINYINT DEFAULT 1 COMMENT '状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表
CREATE TABLE IF NOT EXISTS sys_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    name VARCHAR(50) NOT NULL COMMENT '权限名称',
    code VARCHAR(100) NOT NULL UNIQUE COMMENT '权限编码',
    type VARCHAR(20) COMMENT '权限类型',
    parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS sys_role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 应用表
CREATE TABLE IF NOT EXISTS app_application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '应用ID',
    name VARCHAR(100) NOT NULL COMMENT '应用名称',
    description TEXT COMMENT '应用描述',
    user_id BIGINT NOT NULL COMMENT '创建者ID',
    schema_json LONGTEXT COMMENT '应用Schema配置',
    status VARCHAR(20) DEFAULT 'draft' COMMENT '状态：draft/developing/published',
    version VARCHAR(20) DEFAULT '1.0.0' COMMENT '版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

-- 组件表
CREATE TABLE IF NOT EXISTS app_component (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '组件ID',
    name VARCHAR(100) NOT NULL COMMENT '组件名称',
    type VARCHAR(50) NOT NULL COMMENT '组件类型',
    category VARCHAR(50) COMMENT '组件分类',
    icon VARCHAR(100) COMMENT '组件图标',
    props_json LONGTEXT COMMENT '组件属性配置',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统组件',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_type (type),
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组件表';

-- 模板表
CREATE TABLE IF NOT EXISTS app_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '模板ID',
    name VARCHAR(100) NOT NULL COMMENT '模板名称',
    description TEXT COMMENT '模板描述',
    category VARCHAR(50) COMMENT '模板分类',
    preview_image VARCHAR(255) COMMENT '预览图',
    schema_json LONGTEXT COMMENT '模板Schema配置',
    is_public TINYINT DEFAULT 1 COMMENT '是否公开',
    use_count INT DEFAULT 0 COMMENT '使用次数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_category (category),
    INDEX idx_is_public (is_public)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板表';

-- 版本历史表
CREATE TABLE IF NOT EXISTS app_version (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '版本ID',
    app_id BIGINT NOT NULL COMMENT '应用ID',
    version VARCHAR(20) NOT NULL COMMENT '版本号',
    schema_json LONGTEXT COMMENT '版本Schema配置',
    change_log TEXT COMMENT '变更日志',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_app_id (app_id),
    INDEX idx_version (version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='版本历史表';

-- 部署记录表
CREATE TABLE IF NOT EXISTS app_deployment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '部署ID',
    app_id BIGINT NOT NULL COMMENT '应用ID',
    version VARCHAR(20) COMMENT '部署版本',
    deploy_url VARCHAR(255) COMMENT '部署地址',
    status VARCHAR(20) COMMENT '部署状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_app_id (app_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部署记录表';

-- 初始化角色数据
INSERT INTO sys_role (name, code, description) VALUES
('管理员', 'ADMIN', '系统管理员'),
('普通用户', 'USER', '普通用户');

-- 初始化权限数据
INSERT INTO sys_permission (name, code, type) VALUES
('应用管理', 'app:manage', 'menu'),
('创建应用', 'app:create', 'button'),
('编辑应用', 'app:edit', 'button'),
('删除应用', 'app:delete', 'button'),
('发布应用', 'app:deploy', 'button'),
('模板管理', 'template:manage', 'menu'),
('用户管理', 'user:manage', 'menu');

-- 初始化组件数据
INSERT INTO app_component (name, type, category, icon, props_json, is_system) VALUES
-- 基础组件
('按钮', 'ElButton', '基础组件', 'Pointer', '{"type": "primary", "text": "按钮", "size": "default"}', 1),
('输入框', 'ElInput', '基础组件', 'Edit', '{"placeholder": "请输入", "clearable": true}', 1),
('文本', 'ElText', '基础组件', 'Document', '{"text": "文本内容"}', 1),
('图片', 'ElImage', '基础组件', 'Picture', '{"src": "", "fit": "cover"}', 1),
('链接', 'ElLink', '基础组件', 'Link', '{"text": "链接", "href": "#"}', 1),

-- 表单组件
('表单', 'ElForm', '表单组件', 'Document', '{"labelWidth": "100px"}', 1),
('表单项', 'ElFormItem', '表单组件', 'Document', '{"label": "表单项"}', 1),
('下拉选择', 'ElSelect', '表单组件', 'ArrowDown', '{"placeholder": "请选择", "clearable": true}', 1),
('复选框', 'ElCheckbox', '表单组件', 'Check', '{"label": "复选项"}', 1),
('单选框', 'ElRadio', '表单组件', 'Select', '{"label": "单选项"}', 1),
('开关', 'ElSwitch', '表单组件', 'Switch', '{}', 1),
('日期选择', 'ElDatePicker', '表单组件', 'Calendar', '{"placeholder": "选择日期", "type": "date"}', 1),

-- 布局组件
('容器', 'ElContainer', '布局组件', 'Grid', '{"direction": "vertical"}', 1),
('头部', 'ElHeader', '布局组件', 'Grid', '{"height": "60px"}', 1),
('主体', 'ElMain', '布局组件', 'Grid', '{}', 1),
('底部', 'ElFooter', '布局组件', 'Grid', '{"height": "60px"}', 1),
('行', 'ElRow', '布局组件', 'Grid', '{"gutter": 20}', 1),
('列', 'ElCol', '布局组件', 'Grid', '{"span": 12}', 1),
('卡片', 'ElCard', '布局组件', 'Postcard', '{"header": "卡片标题", "shadow": "hover"}', 1),
('标签页', 'ElTabs', '布局组件', 'Menu', '{}', 1),
('标签面板', 'ElTabPane', '布局组件', 'Menu', '{"label": "标签", "name": "tab1"}', 1),

-- 数据展示
('表格', 'ElTable', '数据展示', 'Grid', '{"data": []}', 1),
('表格列', 'ElTableColumn', '数据展示', 'Grid', '{"prop": "", "label": "列名"}', 1),
('对话框', 'ElDialog', '数据展示', 'ChatDotRound', '{"title": "对话框", "visible": false}', 1);

-- 初始化模板数据
INSERT INTO app_template (name, description, category, schema_json, is_public, use_count) VALUES
('登录表单', '简洁的用户登录表单模板', '表单', '{"name":"登录表单","version":"1.0.0","pages":[{"id":"page_1","name":"登录页","path":"/","components":[{"id":"comp_1","type":"ElCard","props":{"header":"用户登录"},"children":[{"id":"comp_2","type":"ElForm","props":{"labelWidth":"80px"},"children":[{"id":"comp_3","type":"ElFormItem","props":{"label":"用户名"},"children":[{"id":"comp_4","type":"ElInput","props":{"placeholder":"请输入用户名"}}]},{"id":"comp_5","type":"ElFormItem","props":{"label":"密码"},"children":[{"id":"comp_6","type":"ElInput","props":{"placeholder":"请输入密码","type":"password"}}]},{"id":"comp_7","type":"ElFormItem","props":{},"children":[{"id":"comp_8","type":"ElButton","props":{"type":"primary","text":"登录"}}]}]}]}]}],"globalStyle":{"primaryColor":"#409EFF"}}', 1, 100),

('数据列表', '带搜索和分页的数据列表模板', '数据展示', '{"name":"数据列表","version":"1.0.0","pages":[{"id":"page_1","name":"列表页","path":"/","components":[{"id":"comp_1","type":"ElCard","props":{"header":"数据列表"},"children":[{"id":"comp_2","type":"ElRow","props":{"gutter":20},"children":[{"id":"comp_3","type":"ElCol","props":{"span":8},"children":[{"id":"comp_4","type":"ElInput","props":{"placeholder":"搜索关键词","clearable":true}}]},{"id":"comp_5","type":"ElCol","props":{"span":4},"children":[{"id":"comp_6","type":"ElButton","props":{"type":"primary","text":"搜索"}}]},{"id":"comp_7","type":"ElCol","props":{"span":4},"children":[{"id":"comp_8","type":"ElButton","props":{"text":"新增"}}]}]},{"id":"comp_9","type":"ElTable","props":{},"children":[{"id":"comp_10","type":"ElTableColumn","props":{"prop":"name","label":"名称"}},{"id":"comp_11","type":"ElTableColumn","props":{"prop":"status","label":"状态"}},{"id":"comp_12","type":"ElTableColumn","props":{"prop":"createTime","label":"创建时间"}}]}]}]}],"globalStyle":{"primaryColor":"#409EFF"}}', 1, 80),

('仪表盘', '数据统计仪表盘模板', '仪表盘', '{"name":"仪表盘","version":"1.0.0","pages":[{"id":"page_1","name":"仪表盘","path":"/","components":[{"id":"comp_1","type":"ElRow","props":{"gutter":20},"children":[{"id":"comp_2","type":"ElCol","props":{"span":6},"children":[{"id":"comp_3","type":"ElCard","props":{"header":"总用户"},"children":[{"id":"comp_4","type":"ElText","props":{"text":"1,234"}}]}]},{"id":"comp_5","type":"ElCol","props":{"span":6},"children":[{"id":"comp_6","type":"ElCard","props":{"header":"今日访问"},"children":[{"id":"comp_7","type":"ElText","props":{"text":"567"}}]}]},{"id":"comp_8","type":"ElCol","props":{"span":6},"children":[{"id":"comp_9","type":"ElCard","props":{"header":"订单数"},"children":[{"id":"comp_10","type":"ElText","props":{"text":"89"}}]}]},{"id":"comp_11","type":"ElCol","props":{"span":6},"children":[{"id":"comp_12","type":"ElCard","props":{"header":"收入"},"children":[{"id":"comp_13","type":"ElText","props":{"text":"¥12,345"}}]}]}]}]}],"globalStyle":{"primaryColor":"#409EFF"}}', 1, 60);

-- 初始化管理员账号 (密码: admin123)
INSERT INTO sys_user (username, password, email, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'admin@homeworkai.com', 1);
