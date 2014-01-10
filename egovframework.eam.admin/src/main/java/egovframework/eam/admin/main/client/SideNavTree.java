package egovframework.eam.admin.main.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

import egovframework.eam.admin.main.client.data.ExplorerTreeNode;
import egovframework.eam.admin.main.client.data.MenuData;

public class SideNavTree extends TreeGrid {

    private String idSuffix = "";

    private ExplorerTreeNode[] menuData = MenuData.getData(idSuffix);

    public SideNavTree() {
        setWidth100();
        setHeight100();
        setSelectionType(SelectionStyle.SINGLE);
        setCustomIconProperty("icon");
        setAnimateFolderTime(100);
        setAnimateFolders(true);
        setAnimateFolderSpeed(1000);
        setNodeIcon("silk/application_view_list.png");
        setShowSortArrow(SortArrow.CORNER);
        setShowAllRecords(true);
        setLoadDataOnDemand(false);
        setCanSort(false);
        
        TreeGridField field = new TreeGridField();
        field.setCanFilter(true);
        field.setName("nodeTitle");
        field.setTitle("<b>Samples</b>");
        setFields(field);

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setNameProperty("nodeTitle");
        tree.setOpenProperty("isOpen");
        tree.setIdField("nodeID");
        tree.setParentIdField("parentNodeID");
        tree.setRootValue("root" + idSuffix);

        tree.setData(menuData);

        setData(tree);
    }

    public ExplorerTreeNode[] getMenuData() {
        return menuData;
    }
}