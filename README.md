# Hamburger Button

A hamburger button with full customization. Inspired by [VinhLe's idea on the Dribble](https://dribbble.com/shots/1626236-Dribbble-Menu)

![Preview]
(http://i.imgur.com/5paqZ3d.gif)

# Installation
From jCenter
```
compile 'conma.studio:hamburger-button:1.1'

```

# How to use it
You can config the looks of the button through XML layout

```
  <conma.studio.hamburgerbutton.HBButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            hbbutton:border_thickness="10"
            hbbutton:border_corners_radius="15"
            hbbutton:border_color="#6aacff"
            hbbutton:background_color="#f8888b"
            hbbutton:line_thickness="8"
            hbbutton:line_corners_radius="10"
            hbbutton:line_color="#314fff"
            hbbutton:line_width_padding="5"
            hbbutton:slide_left_to_right="true"
            hbbutton:animation_duration="500"/>
```

Or Java code

```
	HBButton hbButton = new HBButton(this);
	hbButton.setBorderThickness(10);
	hbButton.setBorderCornersRadius(15);
	hbButton.setBorderColor(Color.parseColor("#6aacff"));
	hbButton.setBackgroundColor(Color.parseColor("#f8888b"));
	hbButton.setLineThickness(8);
	hbButton.setLineCornersRadius(10);
	hbButton.setLineColor(Color.parseColor("#314fff"));
	hbButton.setLineWidthPadding(5);
	hbButton.setSlideLeftToRight(true);
	hbButton.setAnimationDuration(500);
```

## License
    Copyright 2017 Long Dinh (mr.ribbon@gmail.com)
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.