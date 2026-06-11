import type { Config } from 'tailwindcss'

export default {
    content: [
        "./index.html",
        "./src/**/*.{vue,js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                brand: {
                    bg: '#0B0B0E',
                    surface: '#131317',
                    'surface-bright': '#39393d',
                    'surface-container': '#1f1f23',
                    'sidebar-dark': '#08080b',
                    // Accent Neon Colors
                    primary: '#FF2D78',
                    secondary: '#FF71A4',
                    tertiary: '#00F0FF',
                    // Text Colors
                    'on-surface': '#e4e1e7',
                    'on-surface-dim': '#94949E',
                    'on-surface-bright': '#FFFFFF',
                }
            },
            fontFamily: {
                heading: ['Space Grotesk', 'sans-serif'],
                body: ['Inter', 'sans-serif'],
            },
            boxShadow: {
                'neon-primary': '0 0 16px rgba(255, 45, 120, 0.3)',
                'neon-primary-sm': '0 0 10px rgba(255, 45, 120, 0.1)',
                'neon-tertiary': '0 0 10px rgba(0, 240, 255, 0.15)',
                'neon-alert': '0 0 20px rgba(147, 0, 10, 0.15)',
            },
            textShadow: {
                'glow-primary': '0 0 8px rgba(255, 45, 120, 0.3)',
            },
            backgroundImage: {
                'grid-pattern': 'radial-gradient(rgba(255, 255, 255, 0.05) 1px, transparent 1px)',
            },
            animation: {
                'ui-pulse': 'UI_Pulse 1.5s infinite ease-in-out',
                'ui-ripple': 'UI_Ripple 1.5s infinite ease-out',
            },
            keyframes: {
                UI_Pulse: {
                    '0%, 100%': { opacity: '0.4' },
                    '50%': { opacity: '1' },
                },
                UI_Ripple: {
                    '0%': { transform: 'scale(1)', opacity: '1' },
                    '100%': { transform: 'scale(3)', opacity: '0' },
                }
            }
        },
    },
    plugins: [
        function ({ addUtilities }: any) {
            addUtilities({
                '.text-glow-primary': {
                    textShadow: '0 0 8px rgba(255, 45, 120, 0.3)',
                },
            })
        },
    ],
} satisfies Config