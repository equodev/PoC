<template>
  <v-app id="app">
    <equo-toolbar :close-editor-function="closeEditor">
      <!-- equo-toolitem icon use FontAwesome to its definition. take a look at www.fontawesome.com/icons to choose a icon -->
      <equo-toolitem
        tooltip="Open Folder"
        icon="folder-open"
        :eventHandler="this.openFolder"
      />
      <equo-toolitem tooltip="Save" icon="save" :eventHandler="this.save" />
      <equo-toolitem tooltip="Search" icon="search" :eventHandler="this.find" />
      <equo-toolitem tooltip="Cut" icon="cut" :eventHandler="this.editorCut" />
      <equo-toolitem
        tooltip="Copy"
        icon="copy"
        :eventHandler="this.editorCopy"
      />
      <equo-toolitem
        tooltip="Paste"
        icon="paste"
        :eventHandler="this.editorPaste"
      />
    </equo-toolbar>

    <div class="contentDiv">
      <div class="treeDiv">
        <equo-treeview
          ref="tree"
          title="Explorer"
          :extensionicons="extensionIcons"
          :menuoptions="contextMenuOptions"
        />
      </div>
      <div class="editorShellDiv">
        <equo-editor
          :openEvent="openEditorExtraEvent"
          :closeEvent="closeEditorExtraEvent"
        ></equo-editor>
        <equo-shell class="shellDiv" />
      </div>
    </div>
  </v-app>
</template>

<script>
import EquoTreeview from "./components/Tree.vue";
import EquoToolbar from "./components/Toolbar.vue";
import EquoToolitem from "./components/Toolitem.vue";
import EquoShell from "./components/shell/Shell.vue";
import EquoEditor from "./components/Editor.vue";
import vuetify from "./plugins/vuetify";
import { Menu } from "@equo/equo-application-menu";

export default {
  name: "App",
  vuetify,
  components: {
    EquoTreeview,
    EquoToolbar,
    EquoToolitem,
    EquoShell,
    EquoEditor
  },

  data() {
    return {
      contextMenuOptions: [
        {
          title: "Open",
          eventHandler: function(node, tree) {
            tree.openEditor( node.data.path);
          }
        },
        {
          title: "Cut",
          shortcut: "Ctrl + X",
          eventHandler: function(node, tree) {
            tree.setCutSelection(true);
            tree.setSelectedNode(node);
          }
        },
        {
          title: "Copy",
          shortcut: "Ctrl + C",
          eventHandler: function(node, tree) {
            tree.setCutSelection(false);
            tree.setSelectedNode(node);
          }
        },
        {
          title: "Paste",
          shortcut: "Ctrl + V",
          eventHandler: function(node, tree) {
            tree.pasteFile( node, tree);
          }
        },
        {
          title: "Remove",
          shortcut: "Supr",
          eventHandler: function(node, tree) {
            tree.removeFile( node, tree);
          }
        }
      ],
      menuWithoutEditor: undefined,
      menuWithEditor: undefined
    };
  },

  methods: {
    openEditorExtraEvent() {
      this.menuWithEditor.setApplicationMenu();
    },
    closeEditorExtraEvent() {
      this.menuWithoutEditor.setApplicationMenu();
    },
    openFolder() {
      // eslint-disable-next-line
      equo.openFolder(this.refreshTreeView);
    },
    closeEditor() {
      this.$root.$emit("closeEditor");
    },
    find() {
      this.$root.$emit("editorFind");
    },
    editorCut() {
      this.$root.$emit("editorCut");
    },
    editorCopy() {
      this.$root.$emit("editorCopy");
    },
    editorPaste() {
      this.$root.$emit("editorPaste");
    },
    save() {
      this.$root.$emit("editorSave");
    },
    refreshTreeView(response) {
      this.$root.$emit("refreshTreeView", response);
    }
  },
  created() {
    this.menuWithoutEditor = Menu.create()
      .withMainMenu("File")
        .addMenuItem("Open folder").addShortcut("M1+O").onClick(this.openFolder)
        .addMenuItem("Exit").addShortcut("M1+Q").onClick("exitapphandler");

    this.menuWithEditor = Menu.create()
      .withMainMenu("File")
        .addMenuItem("Open folder").addShortcut("M1+O").onClick(this.openFolder)
        .addMenuItem("Save").addShortcut("M1+S").onClick(this.save)
        .addMenuItem("Exit").addShortcut("M1+Q").onClick("exitapphandler")
      .withMainMenu("Edit")
        .addMenuItem("Cut").addShortcut("M1+X").onClick(this.editorCut)
        .addMenuItem("Copy").addShortcut("M1+C").onClick(this.editorCopy)
        .addMenuItem("Paste").addShortcut("M1+V").onClick(this.editorPaste);

    this.menuWithoutEditor.setApplicationMenu();
  }
};
</script>

<style>
#app {
  height: 100%;
}

.contentDiv {
  display: flex;
  overflow: hidden;
  flex-wrap: unset;
  flex: 1 1 auto;
}

.treeDiv {
  flex: 0 0 25%;
  max-width: 25%;
}

.editorShellDiv {
  flex: 0 0 75%;
  max-width: 75%;
}

.shellDiv {
  height: 300px;
}
</style>
