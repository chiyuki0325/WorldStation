import {StrictMode, useEffect, useState} from 'react'
import {createRoot} from 'react-dom/client'
import {FluentProvider, webLightTheme, webDarkTheme} from '@fluentui/react-components'
import './index.css'
import App from './App.tsx'

const FluentThemedApp = () => {
  // credits: https://medium.com/hypersphere-codes/detecting-system-theme-in-javascript-css-react-f6b961916d48
  const getCurrentTheme = () => window.matchMedia("(prefers-color-scheme: dark)").matches
  const [currentTheme, setCurrentTheme] = useState(getCurrentTheme() ? webDarkTheme : webLightTheme)
  const mqListener = (e: MediaQueryListEvent) => {
    setCurrentTheme(e.matches ? webDarkTheme : webLightTheme)
  }

  useEffect(() => {
    const darkThemeMq = window.matchMedia("(prefers-color-scheme: dark)")
    darkThemeMq.addEventListener("change", mqListener)
    return () => darkThemeMq.removeEventListener("change", mqListener)
  }, [])

  return <FluentProvider theme={currentTheme}>
    <App />
  </FluentProvider>

}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <FluentThemedApp />
  </StrictMode>,
)
