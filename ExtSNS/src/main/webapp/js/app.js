/*
 * Ext.Loader.setConfig({ enabled : true, paths : { 'Ext' : 'js/extjs/src',
 * 'SNS' : 'app', } });
 * Ext.require(['Ext.container.Viewport','Ext.tree.Panel','Ext.Container']);
 */
Ext.Loader.setConfig({
			enabled : true,
			paths : {
				'Ext' : 'js/extjs/src',
				'SNS' : 'js/app'
			}
		});
Ext.require('Ext.direct.*', function() {
			Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
		});
		Ext.require(['Ext.container.Viewport','Ext.tree.Panel','Ext.Container','Ext.data.Store']);
var app = Ext.application({
			name : "SNS",
			funcID:"",
			funcPath:"",
			appFolder : 'js/app',
			controllers : ["Main"],
			autoCreateViewport : true,
			init : function() {
				Ext.setGlyphFontFamily("Pictos");
				Ext.tip.QuickTipManager.init();
				Ext.state.Manager.setProvider(Ext
						.create("Ext.state.CookieProvider"))

			},
			launch : function() {
				this.controllers.addListener('add', this.newControllerAdded,
						this);
				Ext.History.init();
			},

			// 加载Controller后触发事件
			newControllerAdded : function(idx, cntr) {
				cntr.init(this);
			},
			// 判断是否已加载指定Controller，返回Boolean
			hasController : function(name) {
				return !!this.controllers.get(name);
			},
			removeController : function(name)
			{
				this.controllers.removeAtKey( name );
			},
			// 加载并返回指定Controller
			addController : function(name) {
				return this.getController(name);
			}
		})