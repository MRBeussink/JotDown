# JotDown

A lightweight markup language syntax and lexer/parser/tranlator inspired by John Gruber's MarkDown.

## Syntax

| Style                          | HTML                                  | JotDown                     |
| ------------------------------ | ------------------------------------- | --------------------------- |
| Headers                        | `<h1> … </h1>`                        | `#1 ...`                    |
|                                | `<h2> … </h2>`                        | `#2 ...`                    |
|                                | `<h3> … </h3>`                        | `#3 ...`                    |
|                                | `<h4> ... </h4>`                      | `#4 ...`                    |
|                                | `<h5> ... </h5>`                      | `#5 ...`                    |
|                                | `<h6> … </h6>`                        | `#6 ...`                    |
| Horizontal Rule                | `<hr>`                                | `***`                       |
| *Italics*                      | `<i> ital </i>`                       | `_ital_`                    |
| **Bold**                       | `<b> bold </b>`                       | `*bold*`                    |
| <strike>Strikethrough</strike> | `<strike> strike </strike>`           | `-strike-`                  |
| `code`                         | `<code> code </code>`                 | <code>\`code\`</code>       |
| Multi-line Code Block          |                                       | <code> \`\`\` \`\`\`</code> |
| Blockquotes                    | `<blockquote> … </blockquote>`        | ` >> … `                    |
| Links                          | `<a href = "link"> text </a>`         | `[text : link]`             |
| Image                          | `<img src = "source" title = "text">` | `{text : source}`           |
| Ordered List                   |                                       | `- text`                    |
| Unordered List                 |                                       | `* text`                    |
|                                |                                       |                             |
|                                |                                       |                             |

## BNF

| Abstractions     | Definitions                              |
| ---------------- | ---------------------------------------- |
| `<Body> ->`      | `<Header> <Paragraph> newLine`           |
|                  | `horizontalRule newLine`                 |
|                  | `<Paragraph> newLine`                    |
|                  | `quoteBlock <Paragraph> newLine`         |
|                  | `codeBlock <Paragraph> codeBlock`        |
|                  | `orderedList <Paragraph> newLine`        |
|                  | `unOrderedList <Paragraph> newLine`      |
| `<Paragraph> ->` | `bold <Paragraph> bold <Paragraph>`      |
|                  | `ital <Paragraph> ital <Paragraph>`      |
|                  | `strike <Paragraph> strike <Pargraph>`   |
|                  | `code <Paragraph> code <Paragraph>`      |
|                  | `<Link> <Paragraph>`                     |
|                  | `<Image> <Paragraph>`                    |
|                  | `<Text> <Paragraph>`                     |
|                  | `newLine`                                |
| `<Header> ->`    | `h1 <Paragraph> newLine`                 |
|                  | `h2 <Paragraph> newLine`                 |
|                  | `h3 <Paragraph> newLine`                 |
|                  | `h4 <Paragraph> newLine`                 |
|                  | `h5 <Paragraph> newLine`                 |
|                  | `h6 <Paragraph> newLine`                 |
| `<Link> ->`      | `linkOpen <Paragraph> split <Text> linkClose ` |
| `<Image> ->`     | `imageOpen <Text> split <Text> imageClose` |
| `<Text> ->`      | `text <Text>`                            |
