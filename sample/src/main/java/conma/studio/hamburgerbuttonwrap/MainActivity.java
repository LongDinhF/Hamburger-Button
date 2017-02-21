package conma.studio.hamburgerbuttonwrap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import conma.studio.hamburgerbutton.HBButton;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.bt_change_direction).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HBButton hbButton = (HBButton) findViewById(R.id.hb_button6);
				hbButton.setSlideLeftToRight(!hbButton.isSlideLeftToRight());
			}
		});
	}
}