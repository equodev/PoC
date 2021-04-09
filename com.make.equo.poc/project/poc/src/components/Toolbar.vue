<template>
  <div class="toolbar-container">
    <v-toolbar height="35px" color="primary">
      <slot></slot>
      <v-spacer></v-spacer>
      <v-btn
        v-if="crossEnable"
        dark
        icon
        text
        @click="this.closeEditorFunction"
        v-
      >
        <font-awesome-icon :icon="['fas', 'times']" />
      </v-btn>
    </v-toolbar>
  </div>
</template>

<script>
import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(fas);

export default {
  name: "equo-toolbar",
  components: { FontAwesomeIcon },
  props: {
    toolitems: {
      type: Array,
      default() {
        return [];
      }
    },
    closeEditorFunction: {
      type: Function
    }
  },
  data: () => ({
    crossEnable: false
  }),
  methods: {
    toggleCross(value) {
      this.crossEnable = value;
    }
  },
  mounted() {
    this.$root.$on("toolbarToggleCross", value => {
      this.toggleCross(value);
    });
  }
};
</script>
<style>
.toolbar-container {
  height: 35px;
}
</style>
