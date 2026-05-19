declare module '*.vue' {
    import type { DefineComponent } from 'vue'
    // Dit vertelt TypeScript dat elk .vue bestand een geldige Vue component-module is
    const component: DefineComponent<{}, {}, any>
    export default component
}