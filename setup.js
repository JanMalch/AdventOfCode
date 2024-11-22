const fs = require('fs')

const year = process.argv[2]
if (!year) {
    console.error('No year provided. Usage: node setup.js 2024')
    process.exit(1)
}

fs.mkdirSync(`src/y${year}`)

Array(25).fill(0).forEach((_, i) => {
    const day = (i+1).toString().padStart(2, '0')
    fs.mkdirSync(`src/y${year}/d${day}`)
    fs.writeFileSync(`src/y${year}/d${day}/input.txt`, '', 'utf-8');
    const kotlin = part => `package y${year}.d${day}

import inputLines
import println

fun part${part}() {
    val lines = inputLines("y${year}/d${day}/input")
    TODO()
}

`
    fs.writeFileSync(`src/y${year}/d${day}/Part1.kt`, kotlin(1), 'utf-8')
    fs.writeFileSync(`src/y${year}/d${day}/Part2.kt`, kotlin(2), 'utf-8')
    fs.writeFileSync(`src/y${year}/d${day}/Day${day}.kt`, `package y${year}.d${day}\n\nfun main() = part1()\n`, 'utf-8')
})
