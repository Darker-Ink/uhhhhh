package org.darkerink;

import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.setting.NumberSetting;


public class DisconnectModule extends ToggleableModule {

	private final NumberSetting<Integer> daysSetting = new NumberSetting<>("Days", 0, 0, 7)
			.incremental(1);

	private final NumberSetting<Integer> hoursSetting = new NumberSetting<>("Hours", 0, 0, 24)
			.incremental(1);

	private final NumberSetting<Integer> minutesSetting = new NumberSetting<>("Minutes", 0, 0, 60)
			.incremental(1);

	private final NumberSetting<Integer> secondsSetting = new NumberSetting<>("Seconds", 0, 0, 60)
			.incremental(1);

	private final BooleanSetting disableAfterDisconnect = new BooleanSetting("Disable After Disconnect", true);

	private long leaveDate;

	public DisconnectModule() {
		super("Disconnect", "Disconnects you at a specific time", ModuleCategory.MISC);
		this.registerSettings(
				this.daysSetting,
				this.hoursSetting,
				this.minutesSetting,
				this.secondsSetting,
				this.disableAfterDisconnect
		);

	}

	@Override
	public void onEnable() {
		if (mc.level != null) {
			final long currentTime = System.currentTimeMillis();

			final long currentSeconds = currentTime / 1000L;

			final int days = this.daysSetting.getValue();
			final int hours = this.hoursSetting.getValue();
			final int minutes = this.minutesSetting.getValue();
			final int seconds = this.secondsSetting.getValue();

			final long leaveSeconds = (days * 86400L) + (hours * 3600L) + (minutes * 60L) + seconds;

			this.leaveDate = currentSeconds + leaveSeconds;
		}
	}

	@Override
	public void onDisable() {
		this.leaveDate = 0L;
	}

	@Subscribe(stage = Stage.PRE)
	public void tick(EventUpdate event) {
		if (mc.level != null) {
			final long currentSeconds = System.currentTimeMillis() / 1000L;

			if (currentSeconds >= this.leaveDate) {
				this.leaveDate = 0L;

				if (this.disableAfterDisconnect.getValue()) {
					this.toggle();
				}

				mc.level.disconnect();

			}
		}
	}
}