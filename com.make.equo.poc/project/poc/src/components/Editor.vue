<template>
  <div id="editor" class="editor"></div>
</template>
<script>
import { EquoMonaco } from "@equo/equo-monaco-editor";
export default {
  name: "equo-editor",
  props: {
    // custom open Event
    openEvent: {
      type: Function,
      default: () => {}
    },
    // custom close event
    closeEvent: {
      type: Function,
      default: () => {}
    }
  },
  data() {
    return {
      editor: undefined
    };
  },
  methods: {
    thereIsAnEditor() {
      return typeof this.editor !== "undefined";
    },
    doOpenEditor(path) {
      this.editor = EquoMonaco.create(document.getElementById("editor"), path);
      try {
        this.editor.activateShortcuts();
      } catch (err) {
        console.error(err);
      }
      this.openEvent();
    },
    openEditor(path) {
      if (this.thereIsAnEditor()) {
        let app = this;
        this.closeExistingEditor(function() {
          app.editor.dispose();
          app.editor = undefined;
          app.doOpenEditor(path);
        });
      } else {
        this.doOpenEditor(path);
      }
      this.$root.$emit('toolbarToggleCross',true)
    },
    closeEditor() {
      let app = this;
      this.closeExistingEditor(function() {
        app.editor.dispose();
        app.editor = undefined;
      });
      this.$root.$emit('toolbarToggleCross',false)
      this.closeEvent();
    },
    closeExistingEditor(closingFunction) {
      if (this.thereIsAnEditor()) {
        if (this.editor.isDirty()) {
          let app = this;
          // eslint-disable-next-line
          equo.on("_savedialogresponse", function(response) {
            if (response.close) {
              if (response.save) {
                app.editor.setActionDirtyState(function() {
                  if (!app.editor.isDirty()) {
                    closingFunction();
                  }
                });
                app.editor.save();
              } else {
                closingFunction();
              }
            }
          });
          // eslint-disable-next-line
          equo.send("savedialoghandler");
        } else {
          closingFunction();
        }
      }
    },
    getActualFilePath() {
      return this.editor.getFilePath();
    },
    editorFind() {
      if (this.thereIsAnEditor()) {
        this.editor
          .getEditor()
          .getAction("actions.find")
          .run();
      }
    },
    editorCut() {
      if (this.thereIsAnEditor()) {
        this.editor
          .getEditor()
          .getAction("editor.action.clipboardCutAction")
          .run();
      }
    },
    editorCopy() {
      if (this.thereIsAnEditor()) {
        this.editor
          .getEditor()
          .getAction("editor.action.clipboardCopyAction")
          .run();
      }
    },
    editorPaste() {
      if (this.thereIsAnEditor()) {
        this.editor.getEditor().focus();
        document.execCommand("paste");
      }
    },
    save() {
      if (this.thereIsAnEditor()) this.editor.save();
    }
  },
  mounted() {
        // register an event that return a Promise with a value
    this.$root.$on("thereIsAnEditor", (promise) => {
      setTimeout(() => promise.resolve(this.thereIsAnEditor()), 1000);
    });
    this.$root.$on("getActualFilePath", (promise) => {
     setTimeout(() => promise.resolve(this.getActualFilePath()), 1000);
    });
    this.$root.$on("openEditor", path => {
      this.openEditor(path);
    });
    this.$root.$on("conditionalCloseEditor", (path) => {
      if(this.thereIsAnEditor() && path === this.getActualFilePath()){
        this.closeEditor();
      }
    
    });
    this.$root.$on("closeEditor", () => {
      this.closeEditor();
    });
    this.$root.$on("editorSave", () => {
      this.save();
    });
    this.$root.$on("editorCopy", () => {
      this.editorCopy();
    });
    this.$root.$on("editorCut", () => {
      this.editorCut();
    });
    this.$root.$on("editorFind", () => {
      this.editorFind();
    });
    this.$root.$on("editorPaste", () => {
      this.editorPaste();
    });
  }
};
</script>
<style scoped>
.editor {
  height: calc(100% - 270px - 35px);
  background-color: cornflowerblue;
  overflow: hidden;
}
</style>