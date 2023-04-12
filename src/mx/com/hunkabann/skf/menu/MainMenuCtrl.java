package mx.com.hunkabann.skf.menu;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

//import mx.com.hunkabann.skf.frontend.util.WindowBaseCtrl;
import mx.com.hunkabann.skf.frontent.util.WindowBaseCtrl;
import mx.com.hunkabann.skf.menu.dropdown.ZkossDropDownMenuFactory;
import mx.com.hunkabann.skf.workspace.UserWorkspace;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;


/**
 * 
 * Main menu controller. <br>
 * <br>
 * Added the buttons for expanding/closing the menu tree. Calls the menu
 * factory.
 * 
 * @author bbruhns
 * @author sgerth
 * 
 * 
 */
public class MainMenuCtrl extends WindowBaseCtrl implements Serializable {

	private static final long serialVersionUID = -909795057747345551L;
	private transient static final Logger logger = Logger
			.getLogger(MainMenuCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends BaseCtrl' class wich extends Window and implements AfterCompose.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	private transient Window mainMenuWindow; // autowire

	private transient UserWorkspace userWorkspace;

	public void onCreate$mainMenuWindow(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		// doOnCreateCommon(mainMenuWindow, event); // wire vars
		doOnCreateCommon(getMainMenuWindow(), event); // wire vars

		createMenu();
		/* as standard, call the welcome page */
		showPage("/WEB-INF/pages/welcome.zul", "Inicio");
	}

	/**
	 * Creates the mainMenu. <br>
	 * 
	 * @throws InterruptedException
	 */
	private void createMenu() throws InterruptedException {
	

		// correct the desktop height
		Checkbox cb = ((Checkbox) Path.getComponent("/outerIndexWindow/CBtreeMenu"));
		
		System.out.println(Path.getComponent("/outerIndexWindow/CBtreeMenu"));
		cb.setChecked(true);
	
		// get an instance of the borderlayout defined in the index.zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
		
		System.out.println(Path.getComponent("/outerIndexWindow/borderlayoutMain"));
		// get an instance of the searched west layout area

		North north = bl.getNorth();
		north.setFlex(false); // that's important !!!!

		Div div = (Div) north.getFellow("divDropDownMenu");

		Menubar menuBar = (Menubar) div.getFellow("mainMenuBar");
		menuBar.setVisible(false);
		menuBar.getChildren().clear();
		
		West west = bl.getWest();
		west.setWidth("150px");
		west.getChildren().clear();
		west.setVisible(true);
	
		Div div2 = new Div();
		div2.setId("div2");
		div2.setParent(west);
		div2.setHeight("100%");
		Menubar menuBar2 = new Menubar();
		menuBar2.setId("menuBar");
		menuBar2.setHeight("100%");
		menuBar2.setOrient("vertical");
		menuBar2.setParent(div2);
		menuBar2.setScrollable(true);
		menuBar2.setTooltiptext("Haz click para abrir o desplegar el menú");

		Menuitem menuitem = new Menuitem();
		menuitem.setLabel("Menú Horizontal");
		menuitem.setImage("/images/icons/menu_16x16.gif");
		menuitem.setParent(menuBar2);
		
		menuitem.addEventListener("onClick", new EventListener() {
			public void onEvent(Event event) throws Exception {
			mainMenuChange();

			}
		});
		// generate the menu from the menuXMLFile
		ZkossDropDownMenuFactory.addDropDownMenu(menuBar2);
		
		menuitem = new Menuitem();
		menuitem.setLabel("Logout");
		menuitem.setImage("/images/icons/btn_exitdoor2_16x16.gif");
		menuitem.setParent(menuBar2);
		menuitem.setTooltip("Logout");
		
		menuitem.addEventListener("onClick", new EventListener() {
			public void onEvent(Event event) throws Exception {

				//Sessions.getCurrent().getWebApp().removeAttribute(SecurityContextHolder.getContext().getAuthentication().getName());
				getUserWorkspace().doLogout(); // logout.
			}
		});

	}

	/**
	 * Creates a page from a zul-file in a tab in the center area of the
	 * borderlayout. Checks if the tab is opened before. If yes than it selects
	 * this tab.
	 * 
	 * @param zulFilePathName
	 *            The ZulFile Name with path.
	 * @param tabName
	 *            The tab name.
	 * @throws InterruptedException
	 */
	private void showPage(String zulFilePathName, String tabName)
			throws InterruptedException {

		try {
			// TODO get the parameter for working with tabs from the application
			// params
			int workWithTabs = 1;

			if (workWithTabs == 1) {

				/* get an instance of the borderlayout defined in the zul-file */
				Borderlayout bl = (Borderlayout) Path
						.getComponent("/outerIndexWindow/borderlayoutMain");
				/* get an instance of the searched CENTER layout area */
				Center center = bl.getCenter();
				// get the tabs component
				Tabs tabs = (Tabs) center.getFellow("divCenter").getFellow(
						"tabBoxIndexCenter").getFellow("tabsIndexCenter");

				/**
				 * Check if the tab is already opened than select them and<br>
				 * go out of here. If not than create them.<br>
				 */

				Tab checkTab = null;
				try {
					// checkTab = (Tab) tabs.getFellow(tabName);
					checkTab = (Tab) tabs.getFellow("tab_" + tabName.trim());
					checkTab.setSelected(true);
				} catch (ComponentNotFoundException ex) {
					// Ignore if can not get tab.
				}

				if (checkTab == null) {

					Tab tab = new Tab();
					tab.setId("tab_" + tabName.trim());
					tab.setLabel(tabName.trim());
					tab.setClosable(true);

					tab.setParent(tabs);

					Tabpanels tabpanels = (Tabpanels) center.getFellow(
							"divCenter").getFellow("tabBoxIndexCenter")
							.getFellow("tabsIndexCenter").getFellow(
									"tabpanelsBoxIndexCenter");
					Tabpanel tabpanel = new Tabpanel();
					tabpanel.setHeight("100%");
					tabpanel.setStyle("padding: 0px;");
					tabpanel.setParent(tabpanels);
					
					/*
					 * create the page and put it in the tabs area
					 */
					Executions
							.createComponents(zulFilePathName, tabpanel, null);
					tab.setSelected(true);
				}
			} else {
				/* get an instance of the borderlayout defined in the zul-file */
				Borderlayout bl = (Borderlayout) Path
						.getComponent("/outerIndexWindow/borderlayoutMain");
				/* get an instance of the searched CENTER layout area */
				Center center = bl.getCenter();
				/* clear the center child comps */
				center.getChildren().clear();
				/*
				 * create the page and put it in the center layout area
				 */
				Executions.createComponents(zulFilePathName, center, null);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("--> calling zul-file: " + zulFilePathName);
			}
		} catch (Exception e) {
			Messagebox.show(e.toString());
		}
	}

	public void onClick$btnMainMenuExpandAll(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		doCollapseExpandAll(getMainMenuWindow(), true);
	}

	public void onClick$btnMainMenuCollapseAll(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		doCollapseExpandAll(getMainMenuWindow(), false);
	}

	public void mainMenuChange() throws Exception {

		// correct the desktop height
		Checkbox cb = ((Checkbox) Path.getComponent("/outerIndexWindow/CBtreeMenu"));
		cb.setChecked(true);

		// get an instance of the borderlayout defined in the index.zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
		// get an instance of the searched west layout area
		West west = bl.getWest();
		west.setVisible(false);
		west.getChildren().clear();

		North north = bl.getNorth();
		north.setFlex(true); // that's important !!!!

		Div div = (Div) north.getFellow("divDropDownMenu");

		Menubar menuBar = (Menubar) div.getFellow("mainMenuBar");
		menuBar.setVisible(true);
		menuBar.setTooltiptext("Haz click para abrir o desplegar el menú");
		menuBar.setScrollable(true);

		// generate the menu from the menuXMLFile
		ZkossDropDownMenuFactory.addDropDownMenu(menuBar);

		Menuitem menuitem = new Menuitem();
		menuitem.setLabel("Menú Vertical");
		menuitem.setImage("/images/icons/combobox_16x16.gif");
		menuitem.setParent(menuBar);
		
		menuitem.addEventListener("onClick", new EventListener() {
			////@Override
			public void onEvent(Event event) throws Exception {
				// get an instance of the borderlayout defined in the
				// index.zul-file
				createMenu();

			}
		});
		
		menuitem = new Menuitem();
		menuitem.setLabel("Logout");
		menuitem.setImage("/images/icons/btn_exitdoor2_16x16.gif");
		menuitem.setParent(menuBar);
		
		menuitem.addEventListener("onClick", new EventListener() {
			public void onEvent(Event event) throws Exception {

				//Sessions.getCurrent().getWebApp().removeAttribute(SecurityContextHolder.getContext().getAuthentication().getName());
				getUserWorkspace().doLogout(); // logout.
			}
		});
		
		// Refresh the whole page for setting correct sizes of the
		// components
		Window win = (Window) Path.getComponent("/outerIndexWindow");
		win.invalidate();

	}

	private void doCollapseExpandAll(Component component, boolean aufklappen) {
		if (component instanceof Treeitem) {
			Treeitem treeitem = (Treeitem) component;
			treeitem.setOpen(aufklappen);
		}
		Collection<?> com = component.getChildren();
		if (com != null) {
			for (Iterator<?> iterator = com.iterator(); iterator.hasNext();) {
				doCollapseExpandAll((Component) iterator.next(), aufklappen);

			}
		}
	}

	public Window getMainMenuWindow() {
		return mainMenuWindow;
	}

	public void setMainMenuWindow(Window mainMenuWindow) {
		this.mainMenuWindow = mainMenuWindow;
	}

	public UserWorkspace getUserWorkspace() {
		return userWorkspace;
	}

	public void setUserWorkspace(UserWorkspace userWorkspace) {
		this.userWorkspace = userWorkspace;
	}
}
