<p><strong>⚠ IMPORTANT!</strong><br><strong>This mod is <em>NOT</em> compatible with Embeddium!</strong></p>

<p><strong>Flashables</strong> adds <strong>multiplayer-compatible dynamic lights</strong> and a variety of <strong>custom flashlight items</strong> to Minecraft.<br>
Bring light with you wherever you go — no more fumbling in the dark!</p>

<hr>

<h3>🔧 Dependencies</h3>
<p><strong>Required:</strong> Veil (Forge build)</p>
<ul>
  <li>Download: <a href="https://maven.blamejared.com/foundry/veil/Veil-forge-1.20.1/1.0.0.9/Veil-forge-1.20.1-1.0.0.9.jar">Veil-forge-1.20.1-1.0.0.9.jar</a></li>
  <li>Place the JAR in your <code>mods/</code> folder.</li>
  <li><strong>Client-Side Only:</strong> This mod and Veil only need to be installed on the client. The server does not require them.</li>
</ul>

<hr>

<h3>🔦 Features</h3>
<ul>
  <li><p><strong>Dynamic Lighting:</strong> Works in multiplayer without the usual flicker or lag.</p></li>
  <li>
    <p><strong>Custom Flashlights:</strong></p>
    <ul>
      <li><p>Multiple beam colors (17 total, from classic white to vivid pink).</p></li>
      <li><p>Change the color of your flashlight at any time — just combine it with a matching <strong>glass pane</strong> in a crafting grid.</p></li>
    </ul>
  </li>
  <li>
    <p><strong>Battery System:</strong></p>
    <ul>
      <li><p>Flashlights run on replaceable <strong>batteries</strong>.</p></li>
      <li><p>Energy drains while active, so plan ahead!</p></li>
      <li><p>Recharge or swap in fresh ones.</p></li>
    </ul>
  </li>
  <li>
    <p><strong>Crafting Recipes:</strong></p>
    <ul>
      <li><p>Recipes for <strong>flashlights</strong> and <strong>batteries</strong> included right in the mod.</p></li>
      <li><p>Color-changing recipe works with any flashlight and glass pane.</p></li>
    </ul>
  </li>
  <li>
    <p><strong>Dynamic Light Items:</strong></p>
    <ul>
      <li><p>Not just flashlights — many vanilla and modded items can emit light when held!</p></li>
      <li><p>Includes torches, lanterns, froglights, end rods… and a few surprises you’ll have to discover yourself.</p></li>
    </ul>
  </li>
</ul>

<hr>

<h3>🛠 Mod Integration</h3>
<p>You can register your own light items or add compatibility for other mods using the built-in registrar:</p>
<pre>com.smibii.flashables.lights.LightItemRegistry.register(
    Items.TORCH, LightMode.AREA, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND)
);</pre>
<p>Add as many as you like — the system is flexible enough to handle anything from handheld lamps to magical glowing artifacts.</p>

<hr>

<h3>📣 Support / Issues</h3>
<ul>
  <li><strong>Do not report issues to Veil.</strong> If something breaks while using Veil with Flashables, please report it here on the Flashables page/issue tracker. We’ll handle Veil-integration problems on our side.</li>
  <li>Remember: <strong>Embeddium is not supported</strong>. Please remove it before reporting any bugs.</li>
</ul>

<hr>

<p>✅ <strong>Usage &amp; Permissions:</strong><br>
You may include this mod in your modpack or modify it to fit your needs, <strong>but please give proper credit</strong>.</p>
