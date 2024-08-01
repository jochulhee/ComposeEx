# ComposeEx
### Custom Object 구성 및 테스트

- 기본 Component 디자인 구성 변경
- Canvas, Path, Animation   


1. CircleCheckBox

<!-- ![1 circle checkbox](https://github.com/user-attachments/assets/44d164e2-a7b7-497a-bb3d-4dd22dfefa36) -->
<img src="https://github.com/user-attachments/assets/44d164e2-a7b7-497a-bb3d-4dd22dfefa36" width="180" height="200">

```kotlin
fun CircleCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults2.colors()
) { ... }
```
###
###
###
2. Custom Stroke RadioButton 

<!-- ![2 custom stroke radio btn](https://github.com/user-attachments/assets/f704eaca-b86a-46aa-8cb7-58ab29275525) -->
<img src="https://github.com/user-attachments/assets/f704eaca-b86a-46aa-8cb7-58ab29275525" width="180" height="200">

```kotlin
fun RadioButton2(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) { ... }
```
###
###
###
3. InnerThumbSwitch(checked = checkState,

<!-- ![3 inner thumb toggle](https://github.com/user-attachments/assets/50e6679f-8250-47fd-bfb2-44274f118152) -->
<img src="https://github.com/user-attachments/assets/50e6679f-8250-47fd-bfb2-44274f118152" width="180" height="200">

```kotlin
fun InnerThumbSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors(),
) { ... }
```
###
###
###
4. MoonToSunSwitcher

<!-- ![4moontosun](https://github.com/user-attachments/assets/db38d46b-c8e8-4dcc-991a-cf82039e6520) -->
<img src="https://github.com/user-attachments/assets/db38d46b-c8e8-4dcc-991a-cf82039e6520" width="180" height="200">

```kotlin
fun MoonToSunSwitcher(
    isMoon: Boolean,
    sunColor: Color,
    moonColor: Color,
    modifier: Modifier = Modifier,
    animationSpecProgress: AnimationSpec<Float> = tween(1000),
    animationSpecColor: AnimationSpec<Color> = tween(1000)
) { ... }
```
###
###
###
5. RotatePan

<!-- ![5 fan](https://github.com/user-attachments/assets/bf5e3b59-e485-472b-8f46-d9762fc1696a) -->
<img src="https://github.com/user-attachments/assets/bf5e3b59-e485-472b-8f46-d9762fc1696a" width="180" height="200">

```kotlin
fun RotatePan(
    fanColor: Color,
    pinColor: Color,
    modifier: Modifier = Modifier
) { ... }
```


