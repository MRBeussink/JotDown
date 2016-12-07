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

## EBNF

### 

```
<Body> 	 	-> (<Header> | <HR> | <Paragraph> | <Block> | <List>) NL
<Header> 	-> (h1|h2|h3|h4|h5|h6) <Space> <Text> NL
<HR> 	 	-> HR NL
<Paragraph> -> <Text> NL {<Paragraph>}
<Emphasis> 	-> (<Code> | <Ital> | <Bold> | <Strike>) <Text> (<Code> | <Ital> | <Bold> | <Strike>)
<Block>		-> (<CBlock> | <QBlock>) <Text>* (<CBlock> | <QBlock>) NL
<Code Block> -> <CBlock> <Text> <CBlock>
<Block Quote>  -> <QBlock> {<Paragraph>} <QBlock>
<List>		-> {Tab} (<OL> | <UL>) <Space> <ListItem> (<OL> | <UL>) <List>}
<ListItem> -> <Text> NL
<ImageLink> -> ( <Image> | <Link> )
<Image> -> { <Text> : <Text> }
<Link> -> [ <Text> : <Text> ]
<Text> 		-> Any set of characters
```

- [] Add EBNF for Links and Images (and tables?)
- [x] Fix EBNF for Blocks and Lists
