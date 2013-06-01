Ext.define("SNS.view.Main", {
			xtype : "main"
		});
Ext.define("SNS.view.Header", {
			extend : Ext.Container,
			alias : 'widget.appHeader',
			xtype : "appHeader",
			id : "app-header",
			height : 52,
			layout : {
				type : "hbox",
				align : "middle"
			},
			initComponent : function() {
				Ext.apply(this, {
							items : [{
										xtype : "component",
										id : "app-header-title",
										html : "社交营销后台管理",
										flex : 1
									}]
						});
				// alert(Ext.getCmp("options-toolbar"));
				if (!Ext.getCmp("options-toolbar")) {
					this.items.push({
								xtype : "themeSwitcher"
							})
				}
				this.callParent()
			}
		});
Ext.define("SNS.view.Navigation", {
			extend : Ext.tree.Panel,
			xtype : "navigation",
			title : "营销管理平台",
			rootVisible : false,
			lines : false,
			useArrows : true,
			root : {
				expanded : true,
				children : [{
							text : "主数据",
							expanded : true,
							children : [{
										id:"master.Company",
										text : "公司",
										leaf : true
									}, {
										id : "master.Product",
										text : "产品",
										leaf : true
									}, {
										id : "master.ProdCata",
										text : "产品类型",
										leaf : true
									}]
						}, {
							text : "系统设置",
							expanded : true,
							children : [{
										id : "枚举",
										text : "枚举",
										leaf : true
									}, {
										id : "系统参数",
										text : "系统参数",
										leaf : true
									}]
						}]
			}
		});
Ext.define("SNS.view.ContentPanel", {
			extend : Ext.panel.Panel,
			xtype : "contentPanel",
			id : "content-panel",
			title : "&nbsp;",
			forceFit :true,
			autoScroll : true
		});
Ext.define("SNS.view.DescriptionPanel", {
			extend : Ext.panel.Panel,
			xtype : "descriptionPanel",
			id : "description-panel",
			title : "Description",
			autoScroll : true,
			initComponent : function() {
				this.ui = (Ext.themeName === "neptune") ? "light" : "default";
				this.callParent()
			}
		});
Ext.define("SNS.view.CodePreview", {
			extend : Ext.panel.Panel,
			xtype : "codePreview",
			title : "Code Preview",
			autoScroll : true,
			cls : "preview-container",
			tools : [{
						type : "maximize",
						tooltip : "Maximize example code content"
					}],
			initComponent : function() {
				this.ui = (Ext.themeName === "neptune") ? "light" : "default";
				this.callParent()
			}
		});
Ext.define("SNS.view.ThemeSwitcher", function() {
			var a = location.href.match(/ext-theme-(\w+)/);
			a = a && a[1];
			if (!Ext.themeName) {
				Ext.themeName = a
			}
			return {
				extend : Ext.Container,
				xtype : "themeSwitcher",
				id : "theme-switcher",
				margin : "0 10 0 0",
				layout : "hbox",
				items : [{
					xtype : "combo",
					id : "theme-switcher-combo",
					width : 170,
					labelWidth : 50,
					fieldLabel : "Theme",
					displayField : "name",
					valueField : "value",
					margin : "0 5 0 0",
					store : Ext.create("Ext.data.Store", {
								fields : ["value", "name"],
								data : [{
											value : "access",
											name : "Accessibility"
										}, {
											value : "classic",
											name : "Classic"
										}, {
											value : "gray",
											name : "Gray"
										}, {
											value : "neptune",
											name : "Neptune"
										}]
							}),
					value : a,
					listeners : {
						select : function(b) {
							location.href = location.href.replace("ext-theme-"
											+ a, "ext-theme-" + b.getValue())
						}
					}
				}, {
					xtype : "button",
					hidden : true,
					enableToggle : true,
					text : "RTL",
					margin : "0 5 0 0",
					listeners : {
						toggle : function(b, c) {
						}
					}
				}]
			}
		});
