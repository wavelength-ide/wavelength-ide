# Wavelength
<p>
Wavelength is THE IDE for the world's most awesome programming language : the untyped lambda calculus.
</p>

<h3>Getting started</h3>
<p>
Lambda expressions can be written into the editor the usual way
</p>

<pre>(\x.x) y</pre>

<p>
or by using the unicode lambda symbol.
</p>

<pre>(λx.x) y</pre>

<p>
You may also define custom aliases for terms or use predefined library terms by selecting a library in the main menu <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/mainMenu.png?raw=true" height = "14">.
</p>

<pre>id = (λx.x)</pre>

<p>
Insert comments to clearify your code.
</p>
<pre><font color=green>-- welcome to wavelength</font></pre>


<p>
Evaluate your input by pressing <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/run.png?raw=true" height = "14">. The result and all steps leading to the result will be displayed in the output area beneath the editor.
</p>
<p>
Congrats, you just evaluated your first lambda expression!
</p>

<h3>Advanced Usage</h3>
<h4>Execution control</h4>
<p> Instead of evaluating the lambda term from beginning to end with one click, you can step through the evaluation manually by using the <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/backwards.png?raw=true" height = "14"> and <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/forward.png?raw=true" height = "14"> buttons, or by clicking a reducible expression (redex) in the output area (only possible if unicode output format is selected).
</p>
<p>
Pressing <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/run.png?raw=true" height = "14"> or CTRL + ENTER will always start the execution from the beginning, whereas <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/play.png?raw=true" height = "14"> will run the execution from the current point of execution. <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/pause.png?raw=true" height = "14"> will pause a running execution, allowing you to step manually.
</p>
<p>
Use <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/clear.png?raw=true" height = "14">  or CTRL + BACKSPACE to clear the output area.
</p>

<h4>Output format</h4>
<p>
You can choose between two display formats, either unicode or syntax trees, by selecting the dedicated option in the left-most combobox beneath the editor. <br>
The output format cannot be changed during an ongoing execution.
</p>
<p>
The default output format is unicode output, which will display the terms using the unicode lambda symbol (λ). <br>
The redex that will be reduced next (concerning the selected reduction order) is underlined. <br>
Blue highlighted terms can be reduced manually by clicking on them. <br>
The reduced redex will be underlined when the new term is displayed.
</p>

<p>
In syntax trees light blue nodes indicate a redex and the dark blue node is the redex that will be evaluated next concerning the selected reduction order.
</p>

<h4>Reduction order</h4>
<p>
To change the reduction order for the execution process select the desired option in the combobox in the middle. All common orders of the untyped lambda calculus are provided. <br>
Selecting a new reduction order while stepping manually will affect the execution process.
</p>

<h4>Output size</h4>
<p>
In case you do not want to get every step of the execution displayed you can change the output size with the right-most combobox.
</p>
<p>
<b> Full:</b> Every step of the execution is displayed.<br>
<b> Periodically:</b> Every 50th step of the execution is displayed. <br>
<b> Shortened:</b> Only the first and last 10 steps are displayed. <br>
<b> Result only:</b> Only the result is displayed. <br>
</p>

<h4>Libraries</h4>
<p>
To improve your programming experience you can include different libraries by selecting them in the main menu <img src="https://github.com/wavelength-ide/wavelength-ide/blob/master/design/ReadMeImages/mainMenu.png?raw=true" height = "14">. Press CTRL + SPACE in the editor to view the available predefined terms.
</p>
<h5>Church Boolean</h5>
<ul>
  <li><code>true</code></li>
  <li><code>false</code></li>
  <li><code>and</code></li>
  <li><code>or</code></li>
  <li><code>not</code></li>
  <li><code>ifThenElse</code></li>
</ul>

<h5>Natural Numbers</h5>
<p>
Allows you to use arabic numerals in the input. All arithmetic operations provided are prefix expressions.
</p>
<ul>
<li><code>plus</code>: takes two arguments n and m, calculates n + m</li>
    <li><code>minus</code>: takes two arguments n and m, calculates n - m</li>
    <li><code>times</code>: takes two arguments n and m, calculates n * m</li>
    <li><code>pow</code>: takes two arguments n and m, calculates n<sup>m</sup></li>
    <li><code>pred</code>: takes one argument and calculates its predecessor</li>
    <li><code>succ</code>: takes one argument and calculates its successor</li>
</ul>

<h5>Accelerated Natural Numbers</h5>
This library contains the same functionality as Natural Numbers. However, numbers will be displayed as arabic numerals instead of their respective lambda term. Thus arithmetic operations will in general be faster.

<h5>Church Tuples and Lists</h5>
<p>
All provided operations are prefix expressions.
</p>
<ul>
  <li><code>true</code></li>
  <li><code>false</code></li>
  <li><code>pair</code>: takes two parameters n and m, constructs a tuple (n,m)</li>
  <li><code>first</code>: takes a tuple as parameter and returns the first argument</li>
  <li><code>second</code>: takes a tuple as parameter and returns the second argument</li>
  <li><code>newList</code>: constructs a new empty singly linked list</li>
  <li><code>prepend</code>: takes an element and a list of elements as parameter and sets the element as the lists new head</li>
  <li><code>head</code>: takes a list as parameter and returns its first element</li>
  <li><code>tail</code>: takes a list as parameter and returns its last element</li>
  <li><code>isEmpty</code>: returns true if the list contains no elements</li>
</ul>

<h5>Y-Combinator</h5>
<p>
Provides the named term <code>Y</code> to use as the Y-Combinator.
</p>

<h4>Export</h4>
<p>
To copy the current output in a desired format press <img src="https://github.com/wavelength-ide/wavelength-ide/blob/023f3a45e010835c9212214041676f860cfabf6e/design/ReadMeImages/export.png?raw=true" height = "14">
 beneath the output area and select a predefined format. In the current version plaintext, unicode, LaTeX, Haskell and Lisp are support. Notice that we do not guarantee executable Haskell or Lisp code since it is only a syntactic translation.
</p>

<h4>URL Serialization</h4>
<p>
For sharing the current state of the IDE with you friends you can press <img src="https://github.com/wavelength-ide/wavelength-ide/blob/023f3a45e010835c9212214041676f860cfabf6e/design/ReadMeImages/share.png?raw=true" height = "14"> beneath the output area and copy the generated URL.
</p>

<h4>Exercises</h4>
<p>
To test your understanding of the untyped lambda calculus, Wavelength provides different exercises. Each exercise consists of a task description and a sample solution. Just select an exercise in the main menu and master the lambda calculus!
</p>

## License
<p>
Copyright (C) 2018 Muhammet Guemues, Markus Himmel, Marc Huisinga, Philip Klemens, Julia Schmid, Jean-Pierre von der Heydt

Wavelength is licensed under BSD 3-Clause.
</p>
