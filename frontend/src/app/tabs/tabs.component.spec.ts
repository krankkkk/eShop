import { TestBed, fakeAsync, tick, waitForAsync } from '@angular/core/testing';
import { Component } from '@angular/core';
import { TabsModule } from './tabs.module';
import { debounceTime, delay, of } from 'rxjs';

@Component({
  template: `
    <ch-tabs>
      <ch-tab title='Tab1'> Content1 </ch-tab>
      <ch-tab title='Tab2'> Content2</ch-tab>
    </ch-tabs>`,
})
export class TestComponent {
}

describe('Tabs Component', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TabsModule],
      declarations: [TestComponent],
    });
  });

  it('should switch the content when clicking the header', () => {
    const fixture = TestBed.createComponent(TestComponent);
    const element = fixture.nativeElement;
    fixture.autoDetectChanges(true);

    expect(element.querySelector('.tab-content').textContent)
      .toContain('Content1');
    expect(element.querySelector('.tab-content').textContent)
      .not.toContain('Content2');

    element.querySelectorAll('li')[1].click();

    expect(element.querySelector('.tab-content').textContent)
      .not.toContain('Content1');
    expect(element.querySelector('.tab-content').textContent)
      .toContain('Content2');
  });


  it('should allow HTML in the Tab-Body', async () => {
    TestBed.overrideComponent(TestComponent, {
      set: {
        template: `
          <ch-tabs>
            <ch-tab title="Tab1">
                <span id="content">Content1</span>
            </ch-tab>
          </ch-tabs>`
      }
    });
    await TestBed.compileComponents();
    const fixture = TestBed.createComponent(TestComponent);
    fixture.autoDetectChanges(true);
    expect(fixture.nativeElement.querySelector('#content').textContent)
      .toContain('Content1');
  });

  it('should allow HTML (with waitForAsync)', (() => {
    TestBed.overrideComponent(TestComponent, {
      set: {
        template: `
          <ch-tabs>
            <ch-tab title="Tab1">
               <span id="content">Content1</span>
            </ch-tab>
          </ch-tabs>`
      }
    });

    TestBed.compileComponents();
    const fixture = TestBed.createComponent(TestComponent);
    fixture.autoDetectChanges(true);
    expect(fixture.nativeElement.querySelector('#content').textContent)
      .toContain('Content1');
  }));

  it('should wait for all async tasks to complete', waitForAsync(() => {
    of(1).pipe(
      delay(200)
    ).subscribe(x => {
      expect(x).toEqual(1);
    })
  }));

  it('should be possible to simulate time', fakeAsync(() => {
    let called = false;
    setTimeout(() => {
      called = true;
    }, 100);
    expect(called).toBe(false);
    tick(100);
    expect(called).toBe(true);
  }));

});
