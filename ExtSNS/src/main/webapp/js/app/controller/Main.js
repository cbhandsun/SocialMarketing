Ext.define('SNS.controller.Main', {
	extend : Ext.app.Controller,
	views : ['Main'],
	stores : [],
	refs : [{
				ref : 'viewport',
				selector : 'viewport'
			}, {
				ref : 'navigation',
				selector : 'navigation'
			}, {
				ref : 'contentPanel',
				selector : 'contentPanel'
			}, {
				ref : 'descriptionPanel',
				selector : 'descriptionPanel'
			}, {
				ref : 'codePreview',
				selector : 'codePreview'
			}],
	exampleRe : /^\s*\/\/\s*(\<\/?example>)\s*$/,
	init : function() {
		this.control({
					navigation : {
						selectionchange : 'onNavSelectionChange'
					},
					viewport : {
						afterlayout : 'afterViewportLayout'
					},
					'codePreview tool[type=maximize]' : {
						click : 'onMaximizeClick'
					},
					contentPanel : {
					//	resize : 'centerContent'
					}
				})
	},
	afterViewportLayout : function() {
		if (!this.navigationSelected) {
			var d = location.hash.substring(1), a = this.getNavigation(), b = a
					.getStore(), c;
			c = d ? b.getNodeById(d) : b.getRootNode().firstChild.firstChild;
			a.getSelectionModel().select(c);
			a.getView().focusNode(c);
			this.navigationSelected = true
		}
	},
	onNavSelectionChange : function(g, b) {
		var e = b[0], k = e.get('text'), a = e.get('id'), d = 'widget.' + a, l = this
				.getContentPanel(), i;

		if (a) {
			var app = SNS.getApplication( );
			app.funcID = a;
			//alert(app.funcID);
			if ((a.indexOf('master') != -1))
			{
				app.addController(a);
				d = 'widget.'+SNS.getApplication( ).funcID.split(".").pop()+'list'
			}
			else if (!app.hasController(a))
			    app.addController(a);
			

			//this.getController('Company');
			l.removeAll(true);
			// i=Ext.createWidget(a);
			var h = Ext.ClassManager.getNameByAlias(d);
			var j = Ext.ClassManager.get(h);

			//i = Ext.create('SNS.view.company.List');
			
			var c = j.prototype;
			if (c.themes) {
				c.themeInfo = c.themes[Ext.themeName] || c.themes.classic
			}
			//i = Ext.create(j);
			i = Ext.create('SNS.view.'+a+'.List');
			l.add(i);
			if (i.floating) {
				i.show()
			} else {
				//this.centerContent()
			}
			l.setTitle(k);
			document.title = document.title.split(' - ')[0] + ' - ' + k;
			location.hash = a;
			this.updateDescription(c);
			if (c.exampleCode) {
				this.updateCodePreview(c.exampleCode)
			} else {
				this.updateCodePreviewAsync(c, a)
			}
		}
	},
	onMaximizeClick : function() {
		var c = this.getCodePreview(), b = c.getEl().down('.prettyprint').dom.innerHTML;
		var a = new Ext.window.Window({
					autoShow : true,
					title : 'Code Preview',
					modal : true,
					cls : 'preview-container',
					autoScroll : true,
					html : '<pre class="prettyprint">' + b + '</pre>'
				});
		a.maximize()
	},
	processCodePreview : function(g, l) {
		var k = this, p = l.split('\n'), h = false, c = [], d = [], e = p.length, j, o;
		for (j = 0; j < e; ++j) {
			o = p[j];
			if (h) {
				if (k.exampleRe.test(o)) {
					h = false
				}
			} else {
				if (k.exampleRe.test(o)) {
					h = true
				} else {
					d.push(o)
				}
			}
		}
		if (g.themeInfo) {
			var m = ['this', 'themeInfo'];

			function b(q) {
				for (var i in q) {
					var n = q[i];
					m.push(i);
					if (Ext.isPrimitive(n)) {
						if (Ext.isString(n)) {
							n = "'" + n + "'"
						}
						k.replaceValues(d, m.join('.'), n)
					} else {
						b(n)
					}
					m.pop()
				}
			}

			b(g.themeInfo)
		}
		for (j = 0, e = d.length; j < e; ++j) {
			o = d[j];
			if (o.indexOf('themeInfo') < 0) {
				c.push(o)
			}
		}
		var a = c.join('\n');
		a = Ext.htmlEncode(a);
		g.exampleCode = a
	},
	replaceValues : function(b, e, d) {
		var h = b.length, c, g, a;
		for (c = 0; c < h; ++c) {
			a = b[c];
			g = a.indexOf(e);
			if (g >= 0) {
				b[c] = a.split(e).join(String(d))
			}
		}
	},
	updateCodePreview : function(a) {
		this.getCodePreview()
				.update('<pre id="code-preview-container" class="prettyprint">'
								+ a + '</pre>');
		prettyPrint()
	},
	updateCodePreviewAsync : function(a, e) {
		var c = this, b = Ext.ClassManager.getNameByAlias('widget.' + e), d = b
				.replace(/\./g, '/').replace('KitchenSink', 'app')
				+ '.js';
		if (!Ext.repoDevMode) {
			d = '../../../kitchensink/' + d
		}
		Ext.Ajax.request({
					url : d,
					success : function(g) {
						c.processCodePreview(a, g.responseText);
						c.updateCodePreview(a.exampleCode)
					}
				})
	},
	updateDescription : function(a) {
		var c = a.exampleDescription, b = this.getDescriptionPanel();
		if (Ext.isArray(c)) {
			a.exampleDescription = c = c.join('')
		}
		b.update(c)
	},
	centerContent : function() {
		var g = this.getContentPanel(), a = g.body, e = g.items.getAt(0), h = 'c-c', c, b, d;
		if (e) {
			c = (a.getWidth() < (e.getWidth() + 40));
			b = (a.getHeight() < (e.getHeight() + 40));
			if (c && b) {
				h = 'tl-tl', d = [20, 20]
			} else {
				if (c) {
					h = 'l-l';
					d = [20, 0]
				} else {
					if (b) {
						h = 't-t';
						d = [0, 20]
					}
				}
			}
			e.alignTo(g.body, h, d)
		}
	}
});