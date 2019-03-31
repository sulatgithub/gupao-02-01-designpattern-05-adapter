package com.sul.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeAdapter implements ITree {
    private String contextPath;
    private List<Map<String, Object>> list;
    private String idField = "id";
    private String parentField = "parrentId";
    private String nameField = "name";

    public TreeAdapter(List<Map<String, Object>> list,String contextPath) {
        this.list = list;
        this.contextPath = contextPath;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getParentField() {
        return parentField;
    }

    public void setParentField(String parentField) {
        this.parentField = parentField;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    @Override
    public TreeNode getTree(List<Map<String, Object>> list) {
        //图标
        String iconOpen= contextPath + "/files/layui/css/modules/ztree/img/diy/2_open.png";
        String iconClose = contextPath + "/files/layui/css/modules/ztree/img/diy/2_close.png";
        String icon = contextPath + "/files/layui/css/modules/ztree/img/diy/10.png";
        Map<String,TreeNode> tree = new HashMap<>();
        //创建根节点
        TreeNode root = new TreeNode();
        root.setId("0");
        root.setName("");
        root.setOpen(true);
        //将根节点put到map中
        tree.put(root.getId(),root);
        for (Map<String, Object> data : list) {
            //当前id
            String id = (String)data.get(idField);
            //上级id
            String parentId = (String) data.get(parentField);
            if (parentId == null || "".equals(parentId)) {
                parentId = "0";
            }
            String name = (String)data.get(nameField);
            //在map中查找当前节点是否存在，若不存在以当前id为key加入空节点
            TreeNode node = tree.get(id);
            if (node == null) {
                node = new TreeNode();
                tree.put(id,node);
                //新增的节点默认为叶节点

            }
            node.setId(id);
            node.setName(name);
            //查找父节点
            TreeNode parentNode = tree.get(parentId);
            //判断父节点是否存在，若不存在则创建，并加入map
            if (parentNode == null) {
                parentNode = new TreeNode();
                tree.put(parentId,parentNode);
            }
            if (parentNode.getChildren() == null ){
                parentNode.setChildren(new ArrayList<TreeNode>());
            }
            parentNode.getChildren().add(node);
            //含有子节点的节点，设置打开、关闭状态的图标，去掉icon属性
            if(parentNode.getIconOpen() == null) {
                parentNode.setIconOpen(iconOpen);
                parentNode.setIconClose(iconClose);
                parentNode.setIcon(null);
            }
        }
        return root;
    }
}
