# JotDown

A lightweight markup language syntax and lexer/parser/tranlator inspired by John Gruber's MarkDown.

## Syntax

| Style                          | HTML                           | JotDown                     |
| ------------------------------ | ------------------------------ | --------------------------- |
| Headers                        | `<h1> … </h1>`                 | `#1 ...`                    |
|                                | `<h2> … </h2>`                 | `#2 ...`                    |
|                                | `<h3> … </h3>`                 | `#3 ...`                    |
|                                | `<h4> ... </h4>`               | `#4 ...`                    |
|                                | `<h5> ... </h5>`               | `#5 ...`                    |
|                                | `<h6> … </h6>`                 | `#6 ...`                    |
| Horizontal Rule                | `<hr>`                         | `***`                       |
| ------------------------------ | ---------------------------    | ---------------------       |
| *Italics*                      | `<i> ital </i>`                | `_ital_`                    |
| **Bold**                       | `<b> bold </b>`                | `*bold*`                    |
| <strike>Strikethrough</strike> | `<strike> strike </strike>`    | `-strike-`                  |
| `code`                         | `<code> code </code>`          | <code>\`code\`</code>       |
| Multi-line Code Block          |                                | <code> \`\`\` \`\`\`</code> |
| Blockquotes                    | `<blockquote> … </blockquote>` | ` >> … >> `                 |

## EBNF
