# ComposeEx
</br> Custom Object 구성 및 테스트

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
) { .... }
```
</br> 
</br> 
</br> 

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
) { .... }
```
</br>
</br>
</br>

3. InnerThumbSwitch

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
) { .... }
```
</br>
</br>
</br>

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
) { .... }
```
</br>
</br>
</br>

5. RotatePan

<!-- ![5 fan](https://github.com/user-attachments/assets/bf5e3b59-e485-472b-8f46-d9762fc1696a) -->
<img src="https://github.com/user-attachments/assets/bf5e3b59-e485-472b-8f46-d9762fc1696a" width="180" height="200">

```kotlin
fun RotatePan(
    fanColor: Color,
    pinColor: Color,
    modifier: Modifier = Modifier
) { .... }
```
</br>
</br>
</br>

6. Gauge

<img src="https://github.com/user-attachments/assets/8d84e710-d8a7-4f42-854f-bd9cf57a8e09" width="320" height="330">
<img src="https://github.com/user-attachments/assets/452eedcd-f31b-4b77-bcbc-ba4bd4ca78e5" width="320" height="330">

```kotlin
fun Gauge(
    pinColor: Color,
    modifier: Modifier = Modifier,
    markColor: Color,
    markLevel: MarkLevel = MarkLevel.Mid,
    backColor: Color,
    minDegree: Float = 0f,
    maxDegree: Float = 100f,
    degree: Float = 50f,
    fontSize: TextUnit,
    fontColor: Color
) { .... }
```
</br>
</br>
</br>

7. RingProgress

<!-- ![7 RingProgress](https://github.com/user-attachments/assets/065ccd9e-6034-4af8-b367-fc064c713709) -->
<img src="https://github.com/user-attachments/assets/065ccd9e-6034-4af8-b367-fc064c713709" width="187" height="200">

```kotlin
fun RingProgress(
    modifier: Modifier = Modifier,
    barWidth: Float = 0.1f, // 0% ~ 100%
    barColor: Color,
    progressColor: Color,
    degree: Float = 50f,
    maxDegree: Float = 100f,
    fontSize: TextUnit,
    fontColor: Color,
    animationSpecProgress: AnimationSpec<Float> = tween(1500, easing = FastOutSlowInEasing),
) { .... }
```
</br>
</br>
</br>

8. Convex Style Border, Modifier

<img src="https://github.com/user-attachments/assets/e970e063-6ce2-422e-a187-d948015e3bd9" width="335">

```kotlin
Row(
    modifier = Modifier
        .size(300.dp, 60.dp)
        .background(Color(0XFFAAAAAA), CircleShape)
        .convexBorder(
            color = Color(0XFFCCCCDD),
            shape = CircleShape,
            convexStyle = ConvexStyle()
        )
        .padding(horizontal = 20.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) { ... }

fun Modifier.convexBorder(
    color: Color,
    shape: Shape,
    strokeWidth:Dp = 8.dp,
    convexStyle: ConvexStyle = ConvexStyle() // set glare color, shadow color
) { ... }
```
</br>
</br>
</br>

9. Neumorphism Style Object

<img src="https://github.com/user-attachments/assets/2da32896-880f-4d6c-a161-239e1161131c" width="424">

```kotlin
Column(
    modifier = Modifier
        .size(280.dp, 400.dp)
        .background(Color(0xFFAAAAAA))
        .padding(40.dp)
) {
    NeuMorphismButton(
        modifier = Modifier
            .size(50.dp, 50.dp),
        cornerRadius = 10.dp,
        backColor = Color(0xFFAAAAAA),
        text= "Btn"
    ) {
    
    }
}
```
</br>
</br>
</br>
