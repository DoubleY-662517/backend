package com.homeworkai.config;

import com.homeworkai.entity.AppComponent;
import com.homeworkai.entity.AppTemplate;
import com.homeworkai.entity.SysRole;
import com.homeworkai.entity.SysUser;
import com.homeworkai.repository.AppComponentRepository;
import com.homeworkai.repository.AppTemplateRepository;
import com.homeworkai.repository.SysRoleRepository;
import com.homeworkai.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoInitializer implements ApplicationRunner {
    
    private final SysUserRepository sysUserRepository;
    private final SysRoleRepository sysRoleRepository;
    private final AppComponentRepository appComponentRepository;
    private final AppTemplateRepository appTemplateRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(ApplicationArguments args) {
        log.info("开始初始化MongoDB数据...");
        initRoles();
        initAdmin();
        initComponents();
        initTemplates();
        log.info("MongoDB数据初始化完成");
    }
    
    private void initRoles() {
        if (sysRoleRepository.count() == 0) {
            SysRole adminRole = new SysRole();
            adminRole.setName("管理员");
            adminRole.setCode("ADMIN");
            adminRole.setDescription("系统管理员");
            adminRole.setStatus(1);
            adminRole.setCreatedAt(LocalDateTime.now());
            
            SysRole userRole = new SysRole();
            userRole.setName("普通用户");
            userRole.setCode("USER");
            userRole.setDescription("普通用户");
            userRole.setStatus(1);
            userRole.setCreatedAt(LocalDateTime.now());
            
            sysRoleRepository.saveAll(List.of(adminRole, userRole));
            log.info("角色数据初始化完成");
        }
    }
    
    private void initAdmin() {
        if (sysUserRepository.count() == 0) {
            SysUser admin = new SysUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@homeworkai.com");
            admin.setStatus(1);
            admin.setDeleted(0);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            
            sysUserRepository.save(admin);
            log.info("管理员账号初始化完成");
        }
    }
    
    private void initComponents() {
        if (appComponentRepository.count() == 0) {
            List<AppComponent> components = List.of(
                createComponent("按钮", "ElButton", "基础组件", "Pointer", "{\"type\": \"primary\", \"text\": \"按钮\", \"size\": \"default\"}"),
                createComponent("输入框", "ElInput", "基础组件", "Edit", "{\"placeholder\": \"请输入\", \"clearable\": true}"),
                createComponent("文本", "ElText", "基础组件", "Document", "{\"text\": \"文本内容\"}"),
                createComponent("图片", "ElImage", "基础组件", "Picture", "{\"src\": \"\", \"fit\": \"cover\"}"),
                createComponent("链接", "ElLink", "基础组件", "Link", "{\"text\": \"链接\", \"href\": \"#\"}"),
                
                createComponent("表单", "ElForm", "表单组件", "Document", "{\"labelWidth\": \"100px\"}"),
                createComponent("表单项", "ElFormItem", "表单组件", "Document", "{\"label\": \"表单项\"}"),
                createComponent("下拉选择", "ElSelect", "表单组件", "ArrowDown", "{\"placeholder\": \"请选择\", \"clearable\": true}"),
                createComponent("复选框", "ElCheckbox", "表单组件", "Check", "{\"label\": \"复选项\"}"),
                createComponent("单选框", "ElRadio", "表单组件", "Select", "{\"label\": \"单选项\"}"),
                createComponent("开关", "ElSwitch", "表单组件", "Switch", "{}"),
                createComponent("日期选择", "ElDatePicker", "表单组件", "Calendar", "{\"placeholder\": \"选择日期\", \"type\": \"date\"}"),
                
                createComponent("容器", "ElContainer", "布局组件", "Grid", "{\"direction\": \"vertical\"}"),
                createComponent("头部", "ElHeader", "布局组件", "Grid", "{\"height\": \"60px\"}"),
                createComponent("主体", "ElMain", "布局组件", "Grid", "{}"),
                createComponent("底部", "ElFooter", "布局组件", "Grid", "{\"height\": \"60px\"}"),
                createComponent("行", "ElRow", "布局组件", "Grid", "{\"gutter\": 20}"),
                createComponent("列", "ElCol", "布局组件", "Grid", "{\"span\": 12}"),
                createComponent("卡片", "ElCard", "布局组件", "Postcard", "{\"header\": \"卡片标题\", \"shadow\": \"hover\"}"),
                createComponent("标签页", "ElTabs", "布局组件", "Menu", "{}"),
                createComponent("标签面板", "ElTabPane", "布局组件", "Menu", "{\"label\": \"标签\", \"name\": \"tab1\"}"),
                
                createComponent("表格", "ElTable", "数据展示", "Grid", "{\"data\": []}"),
                createComponent("表格列", "ElTableColumn", "数据展示", "Grid", "{\"prop\": \"\", \"label\": \"列名\"}"),
                createComponent("对话框", "ElDialog", "数据展示", "ChatDotRound", "{\"title\": \"对话框\", \"visible\": false}")
            );
            
            appComponentRepository.saveAll(components);
            log.info("组件数据初始化完成");
        }
    }
    
    private AppComponent createComponent(String name, String type, String category, String icon, String propsJson) {
        AppComponent component = new AppComponent();
        component.setName(name);
        component.setType(type);
        component.setCategory(category);
        component.setIcon(icon);
        component.setPropsJson(propsJson);
        component.setIsSystem(1);
        component.setCreatedAt(LocalDateTime.now());
        return component;
    }
    
    private void initTemplates() {
        if (appTemplateRepository.count() == 0) {
            List<AppTemplate> templates = List.of(
                createTemplate("登录表单", "简洁的用户登录表单模板", "表单",
                    "{\"name\":\"登录表单\",\"version\":\"1.0.0\",\"pages\":[{\"id\":\"page_1\",\"name\":\"登录页\",\"path\":\"/\",\"components\":[{\"id\":\"comp_1\",\"type\":\"ElCard\",\"props\":{\"header\":\"用户登录\"},\"children\":[{\"id\":\"comp_2\",\"type\":\"ElForm\",\"props\":{\"labelWidth\":\"80px\"},\"children\":[{\"id\":\"comp_3\",\"type\":\"ElFormItem\",\"props\":{\"label\":\"用户名\"},\"children\":[{\"id\":\"comp_4\",\"type\":\"ElInput\",\"props\":{\"placeholder\":\"请输入用户名\"}}]},{\"id\":\"comp_5\",\"type\":\"ElFormItem\",\"props\":{\"label\":\"密码\"},\"children\":[{\"id\":\"comp_6\",\"type\":\"ElInput\",\"props\":{\"placeholder\":\"请输入密码\",\"type\":\"password\"}}]},{\"id\":\"comp_7\",\"type\":\"ElFormItem\",\"props\":{},\"children\":[{\"id\":\"comp_8\",\"type\":\"ElButton\",\"props\":{\"type\":\"primary\",\"text\":\"登录\"}}]}]}]}]}],\"globalStyle\":{\"primaryColor\":\"#409EFF\"}}",
                    100),
                    
                createTemplate("数据列表", "带搜索和分页的数据列表模板", "数据展示",
                    "{\"name\":\"数据列表\",\"version\":\"1.0.0\",\"pages\":[{\"id\":\"page_1\",\"name\":\"列表页\",\"path\":\"/\",\"components\":[{\"id\":\"comp_1\",\"type\":\"ElCard\",\"props\":{\"header\":\"数据列表\"},\"children\":[{\"id\":\"comp_2\",\"type\":\"ElRow\",\"props\":{\"gutter\":20},\"children\":[{\"id\":\"comp_3\",\"type\":\"ElCol\",\"props\":{\"span\":8},\"children\":[{\"id\":\"comp_4\",\"type\":\"ElInput\",\"props\":{\"placeholder\":\"搜索关键词\",\"clearable\":true}}]},{\"id\":\"comp_5\",\"type\":\"ElCol\",\"props\":{\"span\":4},\"children\":[{\"id\":\"comp_6\",\"type\":\"ElButton\",\"props\":{\"type\":\"primary\",\"text\":\"搜索\"}}]},{\"id\":\"comp_7\",\"type\":\"ElCol\",\"props\":{\"span\":4},\"children\":[{\"id\":\"comp_8\",\"type\":\"ElButton\",\"props\":{\"text\":\"新增\"}}]}]},{\"id\":\"comp_9\",\"type\":\"ElTable\",\"props\":{},\"children\":[{\"id\":\"comp_10\",\"type\":\"ElTableColumn\",\"props\":{\"prop\":\"name\",\"label\":\"名称\"}},{\"id\":\"comp_11\",\"type\":\"ElTableColumn\",\"props\":{\"prop\":\"status\",\"label\":\"状态\"}},{\"id\":\"comp_12\",\"type\":\"ElTableColumn\",\"props\":{\"prop\":\"createTime\",\"label\":\"创建时间\"}}]}]}]}],\"globalStyle\":{\"primaryColor\":\"#409EFF\"}}",
                    80),
                    
                createTemplate("仪表盘", "数据统计仪表盘模板", "仪表盘",
                    "{\"name\":\"仪表盘\",\"version\":\"1.0.0\",\"pages\":[{\"id\":\"page_1\",\"name\":\"仪表盘\",\"path\":\"/\",\"components\":[{\"id\":\"comp_1\",\"type\":\"ElRow\",\"props\":{\"gutter\":20},\"children\":[{\"id\":\"comp_2\",\"type\":\"ElCol\",\"props\":{\"span\":6},\"children\":[{\"id\":\"comp_3\",\"type\":\"ElCard\",\"props\":{\"header\":\"总用户\"},\"children\":[{\"id\":\"comp_4\",\"type\":\"ElText\",\"props\":{\"text\":\"1,234\"}}]}]},{\"id\":\"comp_5\",\"type\":\"ElCol\",\"props\":{\"span\":6},\"children\":[{\"id\":\"comp_6\",\"type\":\"ElCard\",\"props\":{\"header\":\"今日访问\"},\"children\":[{\"id\":\"comp_7\",\"type\":\"ElText\",\"props\":{\"text\":\"567\"}}]}]},{\"id\":\"comp_8\",\"type\":\"ElCol\",\"props\":{\"span\":6},\"children\":[{\"id\":\"comp_9\",\"type\":\"ElCard\",\"props\":{\"header\":\"订单数\"},\"children\":[{\"id\":\"comp_10\",\"type\":\"ElText\",\"props\":{\"text\":\"89\"}}]}]},{\"id\":\"comp_11\",\"type\":\"ElCol\",\"props\":{\"span\":6},\"children\":[{\"id\":\"comp_12\",\"type\":\"ElCard\",\"props\":{\"header\":\"收入\"},\"children\":[{\"id\":\"comp_13\",\"type\":\"ElText\",\"props\":{\"text\":\"¥12,345\"}}]}]}]}]}],\"globalStyle\":{\"primaryColor\":\"#409EFF\"}}",
                    60)
            );
            
            appTemplateRepository.saveAll(templates);
            log.info("模板数据初始化完成");
        }
    }
    
    private AppTemplate createTemplate(String name, String description, String category, String schemaJson, int useCount) {
        AppTemplate template = new AppTemplate();
        template.setName(name);
        template.setDescription(description);
        template.setCategory(category);
        template.setSchemaJson(schemaJson);
        template.setIsPublic(1);
        template.setUseCount(useCount);
        template.setCreatedAt(LocalDateTime.now());
        return template;
    }
}
