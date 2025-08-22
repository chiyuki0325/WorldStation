<script setup>

// credits: Super Mario Bros. X official website, www.smbxgame.com

const {color} = defineProps({
  color: {
    type: String,
    default: 'white', // white, blue
    validator: (value) => ['white', 'blue'].includes(value),
  },
})

</script>

<template>
  <table class="semisolid" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
    <tr :class="['top', `top-${color}`]">
      <td :class="['top-left', `top-left-${color}`]"></td>
      <td :class="['top-center', `top-center-${color}`]"></td>
      <td :class="['top-right', `top-right-${color}`]"></td>
    </tr>
    <tr :class="['middle', `middle-${color}`]">
      <td :class="['middle-left', `middle-left-${color}`]"></td>
      <td :class="['middle-center', `middle-center-${color}`]">
        <slot></slot>
      </td>
      <td :class="['middle-right', `middle-right-${color}`]"></td>
    </tr>
    <tr :class="['bottom', `bottom-${color}`]">
      <td :class="['bottom-left', `bottom-left-${color}`]"></td>
      <td :class="['bottom-center', `bottom-center-${color}`]"></td>
      <td :class="['bottom-right', `bottom-right-${color}`]"></td>
    </tr>
    </tbody>
  </table>
</template>

<style scoped>
  * {
    --unit-height: 24px;
  }

  table.semisolid {
    margin-bottom: var(--unit-height);
    table-layout: fixed;
    border-collapse: collapse;
    border-spacing: 0;
  }

  .top, .bottom {
    height: var(--unit-height);
  }

  .top-left, .top-right, .bottom-left, .bottom-right, .middle-left, .middle-right {
    table-layout: fixed;
    max-height: var(--unit-height);
    max-width: var(--unit-height);
    height: var(--unit-height);
    width: var(--unit-height);
    background-repeat: repeat-y;
    background-size: var(--unit-height) auto;
  }

  .top-center, .bottom-center {
    height: var(--unit-height);
    background-repeat: repeat-x;
    background-size: auto var(--unit-height);
  }

  .top-left-white {
    background-image: url('/static/ss-w/wtl.png');
  }
  .top-center-white {
    background-image: url('/static/ss-w/wtc.png');
  }
  .top-right-white {
    background-image: url('/static/ss-w/wtr.png');
  }
  .middle-left-white {
    background-image: url('/static/ss-w/wml.png');
  }
  .middle-center-white {
    background-color: rgb(224, 224, 224);
  }
  .middle-right-white {
    background-image: url('/static/ss-w/wmr.png');
  }
  .bottom-left-white {
    background-image: url('/static/ss-w/wbl.png');
  }
  .bottom-center-white {
    background-image: url('/static/ss-w/wbc.png');
  }
  .bottom-right-white {
    background-image: url('/static/ss-w/wbr.png');
  }
  .middle-center-white > * {
    color: black;
  }

  .top-left-blue {
    background-image: url('/static/ss-b/btl.png');
  }
  .top-center-blue {
    background-image: url('/static/ss-b/btc.png');
  }
  .top-right-blue {
    background-image: url('/static/ss-b/btr.png');
  }
  .middle-left-blue {
    background-image: url('/static/ss-b/bml.png');
  }
  .middle-center-blue {
    background-color: rgb(88, 200, 248)
  }
  .middle-right-blue {
    background-image: url('/static/ss-b/bmr.png');
  }
  .bottom-left-blue {
    background-image: url('/static/ss-b/bbl.png');
  }
  .bottom-center-blue {
    background-image: url('/static/ss-b/bbc.png');
  }
  .bottom-right-blue {
    background-image: url('/static/ss-b/bbr.png');
  }
  .middle-center-blue > * {
    color: black;
  }
</style>
