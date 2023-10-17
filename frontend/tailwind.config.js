/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,jsx,ts,tsx}"],
  theme: {
    extend: {
      backgroundImage: {
        'quiz': "url(/src/assets/images/quiz_paper.jpg)",
        'money': "url(/src/assets/images/money_background.jpg)",
        'moneyquiz': "url(/src/assets/images/money_quiz.jpg)",
      },
      colors: {
        "main": "#93C1F2",
        "point": "#FFDD9C",
        "background": "#ECECEC",
        "stockRed": "#FF1010",
        "stockBlue": "#2984E7",
        "stockGray": "#7B7B7B",
        "first":"#FFCA10",
        "second":"#BAD1D6",
        "third":"#BC800A",
        "lightYellow":"#FFFBEB",
      },
    },
  },
  plugins: [],
}

